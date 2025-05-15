// eslint-disable-next-line no-unused-vars
import React, { useContext, useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import EditButton from "../../components/common/buttons/EditButton/EditButton";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";
import { userService } from "../../services/services.js";
import { userPhotoService } from "../../services/userPhotoService.js";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { AuthContext } from "../../context/AuthContext";
import { EditableField } from "../../components/common/InputField/EditableField.jsx";
import { FaEdit, FaRegUser } from "react-icons/fa";
import ToggleButton from "../../components/common/buttons/ToggleButton/ToggleButton.jsx";
import "../../styles/page.scss";
import '../../index.scss';
import { API_BASE_URL } from "../../config/config.js";

const UserProfile = ({ isEditing }) => {
    const { userId } = useParams();
    const [loading, setLoading] = useState(true);
    const [userDetails, setUserDetails] = useState(null);
    const [error, setError] = useState(null);
    const [editedFields, setEditedFields] = useState({});
    const { findById, patch } = useAuthenticatedService(userService);
    const { isAuthenticated } = useContext(AuthContext);
    const navigate = useNavigate();

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
    }, [userId, isEditing]);

    const handleFieldChange = (field, value) => {
        setEditedFields((prev) => ({ ...prev, [field]: value }));
    };

    const handleImageUpload = async (event) => {
        const file = event.target.files[0];
        if (!file) return;
        try {
            await userPhotoService.uploadProfileImage(userId, file, isAuthenticated.token);
            navigate(`/profil/${userId}`);
        } catch (err) {
            setError("Échec du téléchargement de l'image.");
            console.error("Error uploading profile image:", err);
        }
    };

    const saveChanges = async () => {
        try {
            const updateFields = { ...editedFields };
            if (updateFields.maxProjects) {
                updateFields.maxProjects = parseInt(updateFields.maxProjects, 10);
            }
            const response = await patch(userId, updateFields, isAuthenticated.token);
            setUserDetails(response);
            setEditedFields({});
        } catch {
            setError("La sauvegarde a échoué.");
        }
    };

    const handleDeleteUser = async (id) => {
        if (window.confirm("Êtes-vous sûr de vouloir supprimer cet utilisateur ?")) {
            try {
                await userService.delete(id, isAuthenticated.token);
                setUserDetails(null);
                navigate(`/utilisateurs`);
            } catch {
                setError("La suppression a échoué.");
            }
        }
    };

    const renderContactInfo = () => (
        <>
            <p><strong>Email :</strong> {userDetails.email}</p>
            <p><strong>Adresse :</strong>
                {userDetails.adresse
                    ? `${userDetails.adresse.rue ?? ""}, ${userDetails.adresse.cpostal ?? ""} ${userDetails.adresse.ville ?? ""}`
                    : "Non renseignée"}
            </p>
            <p><strong>ID :</strong> {userDetails.id}</p>
            <p>
                <strong>Statut du compte :</strong> <span>{userDetails.enabled ? "Actif" : "Inactif"}</span>
            </p>
        </>
    );

    const renderSpecificFields = () => {
        switch (userDetails.role) {
            case "PARRAIN":
                return (
                    <>
                        <p>
                            <strong>Visibilité sur le site : </strong>
                            <span>{userDetails.isActive ? "Visible" : "Invisible"}</span>
                        </p>
                        <p><strong>Parcours :</strong> {userDetails.parcours ?? "Non renseigné"}</p>
                        <p><strong>Expertise :</strong> {userDetails.expertise ?? "Non renseignée"}</p>
                        <p><strong>Déplacements :</strong> {userDetails.deplacement ?? "Non renseignés"}</p>
                        <p><strong>Disponibilités :</strong> {userDetails.disponibilite ?? "Non renseignées"}</p>
                        <p><strong>Nombre max de projets :</strong> {userDetails.maxProjects ?? "Non renseigné"}</p>
                        <p><strong>Projets suivis : </strong>{userDetails.nbrProjetsAffectes ?? "Aucun"}</p>
                    </>
                );
            case "PORTEUR":
                return (
                    <>
                        <p><strong>Disponibilités :</strong> {userDetails.disponibilite ?? "Non renseignées"}</p>
                        <p>
                            <strong>Projet :</strong>{" "}
                            {userDetails.projetTitle ? (
                                <a
                                    href={`/projet/${userDetails.projetId}`}
                                    className="projet-link"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        navigate(`/projet/${userDetails.projetId}`);
                                    }}
                                    aria-label="Voir le projet"
                                >
                                    {userDetails.projetTitle}
                                </a>
                            ) : (
                                <Link to="/creer-projet" className="create-entity" aria-label="Créer un projet">
                                    Créer un projet
                                </Link>
                            )}
                        </p>
                    </>
                );
            default:
                return null;
        }
    };

    const renderEditableFields = () => {
        switch (userDetails.role) {
            case "PARRAIN":
                return (
                    <>
                        <div className="toggle-visibilite-parrain">
                            <label htmlFor="visibilite-parrain">Visible sur le site : </label>
                            <ToggleButton
                                isActive={editedFields.isActive !== undefined ? editedFields.isActive : userDetails.isActive}
                                onChange={(value) => handleFieldChange("isActive", value)}
                            />
                        </div>
                        <EditableField
                            label="Parcours"
                            field="parcours"
                            value={userDetails.parcours}
                            editedFields={editedFields}
                            handleFieldChange={handleFieldChange}
                            aria-label="Modifier le parcours"
                        />
                        <EditableField
                            label="Déplacements"
                            field="deplacement"
                            value={userDetails.deplacement}
                            editedFields={editedFields}
                            handleFieldChange={handleFieldChange}
                            aria-label="Modifier les déplacements"
                        />
                        <EditableField
                            label="Disponibilités"
                            field="disponibilite"
                            value={userDetails.disponibilite}
                            editedFields={editedFields}
                            handleFieldChange={handleFieldChange}
                            aria-label="Modifier les disponibilités"
                        />
                        <div className="input-max-projets">
                            <label htmlFor="max-projects"><strong>Nombre max de projets :</strong></label>
                            <input
                                type="number"
                                id="max-projects"
                                value={editedFields.maxProjects !== undefined ? editedFields.maxProjects : userDetails.maxProjects || ""}
                                onChange={(e) => handleFieldChange("maxProjects", e.target.value)}
                                aria-label="Modifier le maximum admissible de projets suivis"
                            />
                        </div>
                    </>
                );
            case "PORTEUR":
                return (
                    <EditableField
                        label="Disponibilités"
                        field="disponibilite"
                        value={userDetails.disponibilite}
                        editedFields={editedFields}
                        handleFieldChange={handleFieldChange}
                        aria-label="Modifier les disponibilités"
                    />
                );
            default:
                return null;
        }
    };

    if (loading) return <p>Chargement des informations utilisateur...</p>;
    if (error) return <p className="error-message">{error}</p>;
    if (!userDetails) return <p>Aucune information utilisateur disponible.</p>;

    return (
        <div className="container" role="main" aria-labelledby="profile-heading">
            <h1 id="profile-heading">{isEditing ? "Modifier le profil" : "Profil de l'utilisateur"}</h1>
            <div className="profile-picture-wrapper">
                <div className="profile-picture-container">
                    {userDetails?.profilePicture ? (
                        <img
                            className="pictureProfileGrand"
                            src={`${API_BASE_URL}/uploads/${userDetails.profilePicture}`}
                            alt="Avatar utilisateur"
                        />
                    ) : (
                        <FaRegUser size={150} color="#ccc" />
                    )}
                    {isEditing && (
                        <>
                            <label htmlFor="upload-image" className="edit-icon">
                                <FaEdit aria-label="Modifier l'image de profil" tabIndex="0" role="button" />
                            </label>
                            <input
                                id="upload-image"
                                type="file"
                                accept="image/*"
                                style={{ display: "none" }}
                                onChange={handleImageUpload}
                                aria-label="Télécharger une nouvelle image de profil"
                            />
                        </>
                    )}
                </div>
            </div>
            <div className="profile-details">
                <p><strong>Nom d'utilisateur :</strong> {userDetails.firstName} {userDetails.lastName}</p>
                <p><strong>Rôle :</strong> {userDetails.role}</p>

                {/* Informations de contact visibles pour ADMIN, STAFF ou l'utilisateur connecté */}
                {(isAuthenticated.role === "ADMIN" ||
                    isAuthenticated.role === "STAFF" ||
                    isAuthenticated.id === userDetails.id) && renderContactInfo()}

                {/* Champs spécifiques en fonction du rôle */}
                {isEditing ? renderEditableFields() : renderSpecificFields()}

                {/* Actions disponibles */}
                {isEditing ? (
                    Object.keys(editedFields).length > 0 && (
                        <div className="actions">
                            <button onClick={saveChanges} aria-label="Sauvegarder les modifications">Sauvegarder</button>
                            <button onClick={() => setEditedFields({})} aria-label="Annuler les modifications">Annuler</button>
                        </div>
                    )
                ) : (
                    (isAuthenticated.id === userDetails.id || isAuthenticated.role === "ADMIN" || isAuthenticated.role === "STAFF") && (
                        <EditButton onClick={() => navigate(`/profil/edit/${userId}`)} aria-label="Modifier le profil" />
                    )
                )}
                {(isAuthenticated.role === "ADMIN" || isAuthenticated.role === "STAFF") && (
                    <DeleteButton onClick={() => handleDeleteUser(userDetails.id)} aria-label="Supprimer l'utilisateur" />
                )}
            </div>
        </div>
    );
};

export default UserProfile;
