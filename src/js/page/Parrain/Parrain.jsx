import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { userService } from "../../services/services";
import '../../styles/page.scss';
import RenewButton from "../../components/common/buttons/RenewButton/RenewButton.jsx";
import {API_BASE_URL} from "@/config/config.js";

const Parrain = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await userService.getUsersByRole("PARRAIN", isAuthenticated.token);
        setUsers(data);
      } catch (err) {
        setError("Aucun Parrain inscrit sur cette plateforme");
      }
    };
    fetchUsers();
  }, [isAuthenticated.token]);

  const handleUserClick = (userId) => {
    navigate(`/profil/${userId}`);
  };

  function handleRenewActivationCode(id) {
    console.log ("TODO : gerer le renouvellement du code d'activation");
  }
  return (
    <div className="container">
      <h1>Parrains</h1>
      {error && <p className="error-message">{error}</p>}
      <table>
        <thead>
          <tr>
            <th></th>
            {isAuthenticated.role === "ADMIN" || isAuthenticated.role === "STAFF" &&(
            <th>Renouveller activation</th>
            )}
          </tr>
        </thead>
        {isAuthenticated && (
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td onClick={() => handleUserClick(user.id)}>
                  <div className="profile-picture-wrapper-petit">
                    <div className="profile-picture-container-petit">
                      <img className="pictureProfilePetit" src={`${API_BASE_URL}/uploads/${user.profilePicture}`}
                           alt="avatar"/>
                    </div>
                   {user.firstName} {user.lastName}
                  </div>
                </td>
                <td>
                  {isAuthenticated.role === "ADMIN" || isAuthenticated.role === "STAFF" && (
                      <span>
                      {(!user.enabled) && (
                          <RenewButton onClick={() => handleRenewActivationCode(user.id)}/>
                      )}
                   </span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        )}
      </table>
    </div>
  );
};

export default Parrain;
