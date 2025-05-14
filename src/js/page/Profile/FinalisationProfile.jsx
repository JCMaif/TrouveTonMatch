import { useParams, useNavigate } from "react-router-dom";
import React, { useContext, useState } from "react";
import { userService } from "../../services/services.js";
import { AuthContext } from "../../context/AuthContext.jsx";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import "./FinalisationProfile.scss";

const FinalisationProfile = () => {
    const { userId, role } = useParams();
    const { isAuthenticated } = useContext(AuthContext);
    const { finaliser } = useAuthenticatedService(userService);
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        adresse: { rue: "", cpostal: "", ville: "" },
        password: "",
        parcours: role === "PARRAIN" ? "" : undefined,
        expertise: role === "PARRAIN" ? "" : undefined,
        deplacement: role === "PARRAIN" ? "" : undefined,
        maxProjects: role === "PARRAIN" ? "" : undefined,
        disponibilite: "",
        id: userId,
        role: role,
    });

    const [confirmPassword, setConfirmPassword] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [confirmPasswordError, setConfirmPasswordError] = useState("");

    const validatePassword = (password) => {
        if (password.length < 8) return "Le mot de passe doit contenir au moins 8 caractères.";
        if (!/[A-Z]/.test(password)) return "Le mot de passe doit contenir au moins une majuscule.";
        if (!/\d/.test(password)) return "Le mot de passe doit contenir au moins un chiffre.";
        return "";
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const passwordValidationMessage = validatePassword(formData.password);
        if (passwordValidationMessage) {
            setPasswordError(passwordValidationMessage);
            return;
        }

        if (formData.password !== confirmPassword) {
            setConfirmPasswordError("Les mots de passe ne correspondent pas.");
            return;
        }

        try {
            console.log("Données envoyées :", formData);
            await finaliser(userId, {
                ...formData,
                adresse: JSON.stringify(formData.adresse)
            }, isAuthenticated.token);
            alert("Finalisation de l'inscription");
            navigate('/');
        } catch (err) {
            console.error(err);
            alert("Une erreur est survenue");
        }
    };

    return (
        <div>
            <h2>Finalisation du Profil</h2>
            <form onSubmit={handleSubmit} className="container">

                <label htmlFor="password"><strong>Changez de mot de passe : </strong></label>
                <input
                    type="password"
                    placeholder="Mot de passe"
                    value={formData.password}
                    onChange={(e) => {
                        const newPassword = e.target.value;
                        setFormData({...formData, password: newPassword});
                        setPasswordError(validatePassword(newPassword));
                    }}
                    required
                />
                {passwordError && <p className="error-message">{passwordError}</p>}
                <label htmlFor="password"><strong>Confirmez le mot de passe : </strong></label>
                <input
                    type="password"
                    placeholder="Confirmer le mot de passe"
                    value={confirmPassword}
                    onChange={(e) => {
                        setConfirmPassword(e.target.value);
                        setConfirmPasswordError(
                            formData.password !== e.target.value ? "Les mots de passe ne correspondent pas." : ""
                        );
                    }}
                    required
                />
                {confirmPasswordError && <p className="error-message">{confirmPasswordError}</p>}

                <div className="adresse">
                    <span>Adresse :</span>
                    <input
                        type="text"
                        placeholder="Rue"
                        value={formData.adresse.rue}
                        onChange={(e) => setFormData({
                            ...formData,
                            adresse: {...formData.adresse, rue: e.target.value}
                        })}
                        required
                    />
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
                            required
                        />
                        <input
                            type="text"
                            placeholder="Expertise"
                            value={formData.expertise}
                            onChange={(e) => setFormData({...formData, expertise: e.target.value})}
                            required
                        />
                        <input
                            type="text"
                            placeholder="Déplacement"
                            value={formData.deplacement}
                            onChange={(e) => setFormData({...formData, deplacement: e.target.value})}
                        />
                        <div className="input-max-projets">
                            <label htmlFor="max-projects"><strong>Nombre max de projets :</strong></label>
                            <input
                                type="number"
                                id="max-projects"
                                value={formData.maxProjects}
                                onChange={(e) => setFormData({...formData, maxProjects: e.target.value})}
                                aria-label="Modifier le maximum admissible de projets suivis"
                            />
                        </div>
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
            <pre className="styleguide-state-preview">{JSON.stringify({formData}, null, 2)}</pre>
        </div>
    );
};

export default FinalisationProfile;
