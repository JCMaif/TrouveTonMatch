import {useContext, useState} from "react";
import { documentService } from "../../services/services.js";
import { AuthContext } from "../../context/AuthContext";

const UploadDocument = ({ onUploadSuccess }) => {
    const { isAuthenticated } = useContext(AuthContext);
    const [file, setFile] = useState(null);
    const [error, setError] = useState("");
    const [uploading, setUploading] = useState(false);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
        setError("");
    };

    const handleUpload = async (e) => {
        e.preventDefault();
        if (!file) {
            setError("Veuillez sélectionner un fichier à importer.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            setUploading(true);
            await documentService.create(formData, isAuthenticated.token);
            setFile(null);
            onUploadSuccess?.();
        } catch (err) {
            console.error(err);
            setError("Erreur lors de l'envoi du document.");
        } finally {
            setUploading(false);
        }
    };

    return (
        <form onSubmit={handleUpload} className="upload-form">
            <div>
                <input
                    type="file"
                    accept=".pdf,.doc,.docx,.xls,.xlsx,.odt,.ods,image/*"
                    onChange={handleFileChange}
                />
            </div>
            {error && <p className="error-message">{error}</p>}
            <button type="submit" disabled={uploading || !file}>
                {uploading ? "Importation en cours..." : "Importer"}
            </button>
        </form>
    );
};

export default UploadDocument;
