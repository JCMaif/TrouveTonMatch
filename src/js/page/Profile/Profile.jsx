import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import EditButton from "../../components/common/buttons/EditButton/EditButton";
import DeleteButton from "../../components/common/buttons/DeleteButton/DeleteButton.jsx";
import { userService } from "../../services/services.js";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { AuthContext } from "../../context/AuthContext";
import "../../styles/page.scss";

const Profile = () => {
  const { userId } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [userDetails, setUserDetails] = useState(null);
  const [error, setError] = useState(null);
  const { findById } = useAuthenticatedService(userService);
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

  const handleEditUser = () => navigate(`/profil/edit/${userId}`);

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
          <strong>Statut :</strong> <span>{userDetails.enabled ? "Actif" : "Inactif"}</span>
        </p>
      </>
  );

  const renderSpecificFields = () => {
    switch (userDetails.role) {
      case "PARRAIN":
        return (
            <>
              <p><strong>Parcours :</strong> {userDetails.parcours ?? "Non renseigné"}</p>
              <p><strong>Expertise :</strong> {userDetails.expertise ?? "Non renseignée"}</p>
              <p><strong>Déplacements :</strong> {userDetails.deplacements ?? "Non renseignés"}</p>
              <p><strong>Disponibilités :</strong> {userDetails.disponibilitesParrain ?? "Non renseignées"}</p>
            </>
        );
      case "PORTEUR":
        return (
            <>
              <p><strong>Disponibilités :</strong> {userDetails.disponibilitesPorteur ?? "Non renseignées"}</p>
              <p><strong>Projet :</strong> {userDetails.projetName ?? "Non renseigné"}</p>
            </>
        );
      default:
        return null;
    }
  };

  if (loading) return <p>Chargement des informations utilisateur...</p>;
  if (error) return <p className="error-message">{error}</p>;
  if (!userDetails) return <p>Aucune information utilisateur disponible.</p>;

  return (
      <div className="container">
        <h1>Profil de l'utilisateur</h1>
        <div className="profile-details">
          <p><strong>Nom d'utilisateur :</strong> {userDetails.username}</p>
          <p><strong>Rôle :</strong> {userDetails.role}</p>

          {/* Informations de contact visibles pour ADMIN, STAFF ou l'utilisateur connecté */}
          {(isAuthenticated.role === "ADMIN" ||
              isAuthenticated.role === "STAFF" ||
              isAuthenticated.id === userDetails.id) && renderContactInfo()}

          {/* Champs spécifiques en fonction du rôle */}
          {renderSpecificFields()}

          {/* Actions disponibles */}
          {isAuthenticated.role === "ADMIN" && (
              <DeleteButton onClick={() => handleDeleteUser(userDetails.id)} />
          )}
          {(isAuthenticated.id === userDetails.id || isAuthenticated.role === "ADMIN") && (
              <EditButton onClick={handleEditUser} />
          )}
        </div>
      </div>
  );
};

export default Profile;
