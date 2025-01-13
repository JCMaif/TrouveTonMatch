import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import {plateformeService} from "../../services/services";
import "../../styles/page.scss";

const PlateformeDetails = () => {
    const { id } = useParams();
    const [plateforme, setPlateforme] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const { findById } = useAuthenticatedService(plateformeService);

    useEffect(() => {
        if (!id) {
            setError("Invalid plateforme ID");
            setLoading(false);
            return;
        }

        const fetchPlateforme = async () => {
            try {
                const data = await findById(id);
                if (!data) {
                    setError("Plateforme not found");
                } else {
                    setPlateforme(data);
                }
            } catch (err) {
                console.error("Error fetching plateforme details:", err);
                setError("Failed to fetch plateforme details. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchPlateforme();
    }, [id, findById]);

    if (loading) return <div className="loading">Loading plateforme details...</div>;
    if (error) return <p className="error-message">{error}</p>;
    if (!plateforme) return <p>No plateforme found.</p>;

    return (
        <div className="container">
            <h1>{plateforme.nom}</h1>
            <p><strong>Téléphone:</strong> {plateforme.telephone}</p>
        </div>
    );
};

export default PlateformeDetails;
