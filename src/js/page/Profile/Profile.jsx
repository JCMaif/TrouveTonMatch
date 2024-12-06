import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import EditButton from "../../components/common/buttons/EditButton/EditButton";
import "./Profile.scss";

const Profile = () => {
  const { isAuthenticated } = useContext(AuthContext);
  const { username, role, id } = isAuthenticated;
  const navigate = useNavigate();

  const handleEditUser = () => {
    if (id) {
      navigate(`/profil/edit/${id}`);
    } else {
      console.error("ID utilisateur non défini, navigation annulée.");
    }
  };

  return (
    <div className="profile-container">
      <h1>Mon Profil</h1>
      {username ? (
        <div className="profile-details">
          <p><strong>Nom d'utilisateur :</strong> {username}</p>
          <p><strong>Rôle :</strong> {role}</p>
          <p><strong>ID :</strong> {id}</p>
          <EditButton onClick={handleEditUser} />
        </div>
      ) : (
        <p>Vous n'êtes pas connecté. Veuillez vous connecter pour voir votre profil.</p>
      )}
    </div>
  );
};

export default Profile;
