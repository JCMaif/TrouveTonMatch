import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { projetService } from "../../services/services";
import "../../styles/page.scss";

const ProjetDetails = () => {
    const { projetId } = useParams();
    const [projet, setProjet] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const { findById } = useAuthenticatedService(projetService);

    useEffect(() => {
        const fetchProjet = async () => {
            try {
                const data = await findById(projetId);
                setProjet(data);
            } catch (err) {
                setError("Failed to fetch project details");
            } finally {
                setLoading(false);
            }
        };

        fetchProjet();
    }, [projetId]);

    if (loading) return <p>Loading project details...</p>;
    if (error) return <p className="error-message">{error}</p>;
    if (!projet) return <p>No project found.</p>;

    return (
        <div className="container">
            <h1>{projet.title}</h1>
            <p><strong>Porteur : </strong> {projet.porteur.username}</p>
            <p><strong>Date de lancement:</strong> {new Date(projet.startingDate).toLocaleDateString()}</p>
            <p><strong>Description:</strong> {projet.description}</p>
        </div>
    );
};

export default ProjetDetails;
