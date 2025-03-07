import { useState, useEffect, useContext } from 'react';
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { userService } from '../../services/services';
import '../../styles/page.scss';
import RenewButton from "../../components/common/buttons/RenewButton/RenewButton.jsx";
import { API_BASE_URL } from "../../config/config.js";
import { FaRegUser } from "react-icons/fa";

const Porteur = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated?.token) return;

    const fetchUsers = async () => {
      try {
        const data = await userService.getUsersByRole('PORTEUR', isAuthenticated.token);
        setUsers(data);
      } catch (err) {
        setError('Échec de la récupération des utilisateurs.');
      }
    };

    fetchUsers();
  }, [isAuthenticated?.token]);

  const handleUserClick = (userId) => {
    navigate(`/profil/${userId}`);
  };

  function handleRenewActivationCode(id) {
    console.log("TODO : gérer le renouvellement du code d'activation");
  }

  return (
      <div className="container">
        <h1>Porteurs de projet</h1>
        {error && <p className="error-message">{error}</p>}
        <table>
          <thead>
          <tr>
            <th></th>
            {(isAuthenticated?.role === "ADMIN" || isAuthenticated?.role === "STAFF") && (
                <th>Renouveler activation</th>
            )}
          </tr>
          </thead>
          <tbody>
          {users.map((user) => (
              <tr key={user.id}>
                <td onClick={() => handleUserClick(user.id)}>
                  <div className="profile-picture-wrapper-petit">
                    <div className="profile-picture-container-petit">
                      {user?.profilePicture ? (
                          <img
                              className="pictureProfilePetit"
                              src={`${API_BASE_URL}/uploads/${user.profilePicture}`}
                              alt="image utilisateur"
                          />
                      ) : (
                          <FaRegUser size={50} color="#ccc" />
                      )}
                    </div>
                    {user.firstName} {user.lastName}
                  </div>
                </td>
                {(isAuthenticated?.role === "ADMIN" || isAuthenticated?.role === "STAFF") && (
                    <td>
                  <span>
                    {!user.enabled && (
                        <RenewButton onClick={() => handleRenewActivationCode(user.id)} />
                    )}
                  </span>
                    </td>
                )}
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
};

export default Porteur;
