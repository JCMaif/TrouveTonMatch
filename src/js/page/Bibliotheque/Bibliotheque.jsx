import { useContext, useEffect, useState } from 'react';
import { AuthContext } from "../../context/AuthContext.jsx";
import { documentService, userService } from "../../services/services.js";
import {Link, useNavigate} from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";
import DownloadButton from "../../components/common/buttons/DownloadButton/DownloadButton.jsx";


const Bibliotheque = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const [documents, setDocuments] = useState([]);
    const [userMap, setUserMap] = useState({});
    const [error, setError] = useState(null);
    useNavigate();
    const { findAll} = useAuthenticatedService(documentService);

    const userCanCRUD = isAuthenticated && (
        isAuthenticated.role === "ADMIN" ||
        isAuthenticated.role === "STAFF"
    );

    useEffect(() => {
        const fetchDocuments = async () => {
            setError("");
            try {
                const docs = await findAll();
                setDocuments(docs);

                const uploaderIds = [
                    ...new Set(docs
                        .map(doc => doc.uploadedBy)
                        .filter(Boolean))
                ];

                const userResponses = await Promise.all(
                    uploaderIds.map(id => userService.findById(id, isAuthenticated.token))
                );

                const userMap = {};
                userResponses.forEach(user => {
                    userMap[user.id] = user.username;
                });
                setUserMap(userMap);
            } catch (e) {
                setError("Échec du chargement des documents");
                console.error(e);
            }
        };

        fetchDocuments();
    }, []);

    const handleClick = async (id) => {
        setError(null);
        try {
            const response = await documentService.getDocumentById(id, isAuthenticated.token)

            if (!response.ok) throw new Error("Téléchargement échoué");

            const disposition = response.headers.get("Content-Disposition");
            console.log("disposition :", disposition);
            let fileName = "document";
            if (disposition) {
                const match = disposition.match(/filename\*?=(?:UTF-8''|")?([^";\n]+)/i);
                if (match && match[1]) {
                    fileName = decodeURIComponent(match[1]);
                }
            }

            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);

            const a = document.createElement("a");
            a.href = url;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            a.remove();

            window.URL.revokeObjectURL(url);
        } catch (e) {
            console.error(e);
            setError("Échec du chargement du document");
        }
    };


    const handleDeleteDocument = async (id) => {
        if (window.confirm("Supprimer ce document ?")) {
            setError("");
            try {
                await documentService.delete(id, isAuthenticated.token);
                setDocuments(documents.filter(doc => doc.id !== id));
            } catch {
                setError("Erreur dans la suppression du document");
            }
        }
    }

    return (
        <div className="container">
            <h1>Documents</h1>
            {error && <p className="error-message">{error}</p>}
            {userCanCRUD && (
                <li className='create-entity'>
                    <Link to="/upload" className="Nav-link">Importer un document</Link>
                </li>
            )}
            <table>
                <thead>
                <tr>
                    <th>Nom</th>
                    <th className="col-optionel">Type</th>
                    <th className="col-optionel">Mis en ligne le</th>
                    <th className="col-optionel">Mis en ligne par</th>
                    <th>Actions</th>

                </tr>
                </thead>
                <tbody>
                {documents.map((doc) => (
                    <tr key={doc.id}>
                        <td>{doc.name}</td>
                        <td className="col-optionel">{doc.type}</td>
                        <td className="col-optionel">{new Date(doc.uploadedAt).toLocaleString()}</td>
                        <td className="col-optionel">{userMap[doc.uploadedBy] || "—"}</td>
                        <td><DownloadButton onClick={() => handleClick(doc.id)} />
                            {userCanCRUD && (
                                <DeleteButton onClick={() => handleDeleteDocument(doc.id)} aria-label="Supprimer le document" />
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Bibliotheque;
