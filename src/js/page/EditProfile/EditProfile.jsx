import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { userService } from "../../services/services.js";
import { AuthContext } from "../../context/AuthContext.jsx";
import { EditableField } from "../../components/common/InputField/EditableField.jsx";
import { FaEdit } from "react-icons/fa";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";

const EditProfile = () => {
    const { userId } = useParams();
    const [loading, setLoading] = useState(true);
    const [userDetails, setUserDetails] = useState(null);
    const [error, setError] = useState(null);
    const [editedFields, setEditedFields] = useState({});
    const { findById, patch } = useAuthenticatedService(userService);
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
        setEditedFields((prev) => ({ ...prev, [field]: value }));
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

    if (loading) return <p>Chargement des informations utilisateur...</p>;
    if (error) return <p className="error-message">{error}</p>;
    if (!userDetails) return <p>Aucune information utilisateur disponible.</p>;

    return (
        <div className="container">
            <h1>Modifier le profil</h1>
            <div className="profile-details">
                <p className="hint">Cliquer sur un champ modifiable ( <FaEdit className="edit-icon"/>) pour le modifier</p>
                <p><strong>Nom d'utilisateur : {userDetails.username}</strong></p>
                <span><strong>Email : </strong> {userDetails.email}</span>
                <p><strong>Adresse : </strong></p>
                <EditableField label="Rue " field="rue" value={userDetails.adresse.rue} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                <EditableField label="Code postal " field="cpostal" value={userDetails.adresse.cpostal} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                <EditableField label="Ville " field="ville" value={userDetails.adresse.ville} editedFields={editedFields} handleFieldChange={handleFieldChange} />

                {userDetails.role === "PARRAIN" && (
                    <>
                        <EditableField label="Parcours" field="parcours" value={userDetails.parcours} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                        <EditableField label="Expertise" field="expertise" value={userDetails.expertise} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                        <EditableField label="Déplacements" field="deplacement" value={userDetails.deplacement} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                        <EditableField label="Disponibilités" field="disponibilite" value={userDetails.disponibilite} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                    </>
                )}
                {userDetails.role === "PORTEUR" && (
                    <>
                        <EditableField label="Disponibilités" field="disponibilite" value={userDetails.disponibilite} editedFields={editedFields} handleFieldChange={handleFieldChange} />
                        <p><strong>Projet : </strong>{userDetails.projetTitle}</p>
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
