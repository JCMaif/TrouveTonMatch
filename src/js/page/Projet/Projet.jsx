import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { projetService } from "../../services/services";
import "../../styles/page.scss";

const Projets = () => {
    const [projets, setProjets] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { findAll } = useAuthenticatedService(projetService);

    useEffect(() => {
        const fetchProjets = async () => {
            try {
                const data = await findAll();
                console.log("projets",data);
                setProjets(data);
            } catch (err) {
                setError("Failed to fetch projects");
            }
        };

        fetchProjets();
    }, []);

    const handleClick = (projetId) => navigate(`/projet/${projetId}`);

    return (
        <div className="container">
            <h1>Projets</h1>
            {error && <p className="error-message">{error}</p>}
            <table>
                <thead>
                <tr>
                    <th>Titre</th>
                    <th>Porteur</th>
                    <th>Parrain</th>
                </tr>
                </thead>
                <tbody>
                {projets.map((projet) => (
                    <tr key={projet.id} onClick={() => handleClick(projet.id)}>
                        <td>{projet.title}</td>
                        <td>{projet.porteurFirstName} {projet.PorteurLastname}</td>
                        <td>{projet.parrainFirstName} {projet.ParrainLastname}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Projets;
