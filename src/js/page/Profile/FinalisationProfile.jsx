import { useParams, useNavigate } from "react-router-dom";
import {useContext, useState} from "react";
import {userService} from "../../services/services.js";
import {AuthContext} from "../../context/AuthContext.jsx";
import "./FinalisationProfile.scss";

const FinalisationProfile = () => {
    const { userId, role } = useParams();
    const { isAuthenticated } = useContext(AuthContext);
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        adresse: { rue: "", cpostal: "", ville: "" },
        parcours: role === "PARRAIN" ? "" : undefined,
        expertise: role === "PARRAIN" ? "" : undefined,
        deplacement: role === "PARRAIN" ? "" : undefined,
        disponibilite: "",
    });

    const handleSubmit = async (e) => {
            e.preventDefault();
            try {
                const data = await userService.finaliser(userId, formData, isAuthenticated.token);
                alert(`Finalisation de l'inscription`);
                navigate("/");
            } catch (err) {
                console.error(err);
                alert("Une erreur est survenue");
            }
        };

    return (
        <div>
            <h2>Finalisation du Profil</h2>
            <form onSubmit={handleSubmit} className="container">
                <div className="adresse">
                    <span>Adresse :</span>
                    <input type="text"
                           placeholder="Rue"
                           value={formData.adresse.rue}
                           onChange={(e) => setFormData({
                               ...formData,
                               adresse: { ...formData.adresse, rue: e.target.value }
                           })}
                           required/>
                    <input
                        type="text"
                        placeholder="Code Postal"
                        value={formData.adresse.cpostal}
                        onChange={(e) => setFormData({
                            ...formData,
                            adresse: {...formData.adresse, cpostal: e.target.value}
                        })}
                        required
                    />
                    <input
                        type="text"
                        placeholder="Ville"
                        value={formData.adresse.ville}
                        onChange={(e) => setFormData({
                            ...formData,
                            adresse: {...formData.adresse, ville: e.target.value}
                        })}
                        required
                    />
                </div>

                {role === "PARRAIN" && (
                    <>
                        <input
                            type="text"
                            placeholder="Parcours"
                            value={formData.parcours}
                            onChange={(e) => setFormData({...formData, parcours: e.target.value})}
                            required/>
                        <input
                            type="text"
                            placeholder="Expertise"
                            value={formData.expertise}
                            onChange={(e) => setFormData({...formData, expertise: e.target.value})}
                            required/>
                        <input
                            type="text"
                            placeholder="Déplacement"
                            value={formData.deplacement}
                            onChange={(e) => setFormData({...formData, deplacement: e.target.value})}
                        />
                    </>
                )}

                <input
                    type="text"
                    placeholder="Disponibilité"
                    value={formData.disponibilite}
                    onChange={(e) => setFormData({...formData, disponibilite: e.target.value})}
                />

                <button type="submit">Finaliser</button>
            </form>
        </div>
    );
};

export default FinalisationProfile;