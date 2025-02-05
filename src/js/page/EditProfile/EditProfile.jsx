import React, {useContext, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useAuthenticatedService} from "../../hook/useAuthenticatedService.js";
import {userService} from "../../services/services.js";
import {AuthContext} from "../../context/AuthContext.jsx";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";

const EditProfile = () => {
    const { userId } = useParams();
    const [loading, setLoading] = useState(true);
    const [userDetails, setUserDetails] = useState(null);
    const [error, setError] = useState(null);
    const [editedFields, setEditedFields] = useState({});
    const { findById , patch} = useAuthenticatedService(userService);
    const { isAuthenticated } = useContext(AuthContext);

    useEffect(() => {
        const fetchUserDetails = async () => {
            setError(null);
            try {
                const response = await findById(userId);
                setUserDetails(response);
            } catch {
                setError("Impossible de charger les informations utilisateur.");
            } finally {
                setLoading(false);
            }
        };

        fetchUserDetails();
    }, [userId]);

    const handleFieldChange = (field, value) => {
        setEditedFields((prev) => ({...prev, [field]: value }));
    }

    const saveChanges = async () => {
        try {
            const response = await patch(userId, editedFields, isAuthenticated.token);
            setUserDetails(response);
            setEditedFields({});
        } catch {
            setError("La sauvegarde a échoué.");
        }
    };

    const renderEditableField = (label, field, value) => (
        <div className="editable-field">
            <label><strong>{label} :</strong></label>
            {editedFields[field] !== undefined ? (
                <input
                    type="text"
                    value={editedFields[field]}
                    onChange={(e) => handleFieldChange(field, e.target.value)}
                />
            ) : (
                <span onClick={() => handleFieldChange(field, value)}>{value || "Non renseigné"}</span>
            )}
        </div>
    );

    if (loading) return <p>Chargement des informations utilisateur...</p>;
    if (error) return <p className="error-message">{error}</p>;
    if (!userDetails) return <p>Aucune information utilisateur disponible.</p>;

    return (
        <div className="container">
            <h1>Modifier le profil</h1>
            <div className="profile-details">
                <p><strong>Nom d'utilisateur : {userDetails.username}</strong></p>
                {renderEditableField("Email", "email", userDetails.email)}
                {renderEditableField("Adresse", "rue", userDetails.adresse.rue)}
                {renderEditableField(" ", "cpostal", userDetails.adresse.cpostal)}
                {renderEditableField(" ", "ville", userDetails.adresse.ville)}

                {userDetails.role === "PARRAIN" && (
                    <>
                        {renderEditableField("Parcours", "parcours", userDetails.parcours)}
                        {renderEditableField("Expertise", "expertise", userDetails.expertise)}
                        {renderEditableField("Déplacements", "deplacements", userDetails.deplacements)}
                        {renderEditableField(
                            "Disponibilités",
                            "disponibilitesParrain",
                            userDetails.disponibilitesParrain
                        )}
                    </>
                )}
                {userDetails.role === "PORTEUR" && (
                    <>
                        {renderEditableField(
                            "Disponibilités",
                            "disponibilitesPorteur",
                            userDetails.disponibilitesPorteur
                        )}
                        <p><strong>Projet : </strong>{userDetails.projetName}</p>
                    </>
                )}

                {Object.keys(editedFields).length > 0 && (
                    <div className="actions">
                        <button onClick={saveChanges}>Sauvegarder</button>
                        <button onClick={() => setEditedFields({})}>Annuler</button>
                    </div>
                )}
                {isAuthenticated.role === "ADMIN" && (
                    <DeleteButton onClick={() => handleDeleteUser(userDetails.id)} />
                )}
            </div>
        </div>
    );
};
export default EditProfile;