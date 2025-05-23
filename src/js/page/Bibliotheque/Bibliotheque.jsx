import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from "../../context/AuthContext.jsx";
import { documentService, userService } from "../../services/services.js";
import {Link, useNavigate} from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";
import DownloadButton from "../../components/common/buttons/DownloadButton/DownloadButton.jsx";
import {API_BASE_URL} from "@/config/config.js";


const Bibliotheque = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const [documents, setDocuments] = useState([]);
    const [userMap, setUserMap] = useState({});
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { findAll,getDocumentById} = useAuthenticatedService(documentService);

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

    const handleClick = (id) => {
        const token = isAuthenticated.token;
        const fetchDocument = async () => {
            try {
                const doc = await getDocumentById(id, token);
                const blob = await doc.blob();
                console.log("document : ", doc);
                const file = window.URL.createObjectURL(blob);
                window.location.assign(file);
                // window.open(`${API_BASE_URL}/uploads/${doc.path}`, '_blank');
                // const a = document.createElement("a");
                // a.href = file;
                // a.download = "document.pdf";
                // document.body.appendChild(a);
                // a.click();
                // document.body.removeChild(a);

            }catch {
                setError("Échec du chargement du document");
            }
        }
        fetchDocument();
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
                    <th>Type</th>
                    <th>Mis en ligne le</th>
                    <th>Mis en ligne par</th>
                    <th>Actions</th>

                </tr>
                </thead>
                <tbody>
                {documents.map((doc) => (
                    <tr key={doc.id}>
                        <td>{doc.name}</td>
                        <td>{doc.type}</td>
                        <td>{new Date(doc.uploadedAt).toLocaleString()}</td>
                        <td>{userMap[doc.uploadedBy] || "—"}</td>
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
