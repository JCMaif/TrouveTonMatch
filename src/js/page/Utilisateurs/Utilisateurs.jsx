import { userService } from "../../services/services";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "../../styles/page.scss";
import EditButton from "@/components/common/buttons/EditButton/EditButton.jsx";
import DeleteButton from "@/components/common/buttons/DeleteButton/DeleteButton.jsx";
import RenewButton from "@/components/common/buttons/RenewButton/RenewButton.jsx";

const Utilisateurs = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      setError("");
      try {
        const data = await userService.findAll(isAuthenticated.token);
        setUsers(data);
      } catch (err) {
        setError("Failed to fetch users");
      }
    };

    fetchUsers();
  }, [isAuthenticated.token]);

  const handleUserClick = (userId) => navigate(`/profil/${userId}`);
  const handleEditUser = (userId) => navigate(`/profil/edit/${userId}`);
  const handleDeleteUser = async (userId) => {
    if (window.confirm("Are you sure you want to delete this user?")) {
      try {
        await userService.delete(userId, isAuthenticated.token);
        setUsers(users.filter((user) => user.id !== userId));
      } catch (err) {
        setError("Failed to delete user");
      }
    }
  };

  console.log("users : " , users);

  const usersByPlateform = users.reduce((acc, user) => {
    const plateformeName = user.plateformeName || "hors plateforme";
    if (!acc[plateformeName]) {
      acc[plateformeName] = [];
    }
    acc[plateformeName].push(user);
    return acc;
  }, {});

  function handleRenewActivationCode(id) {
    console.log ("TODO : gerer le renouvellement du code d'activation");
  }

  return (
      <div className="container">
        <h1>Utilisateurs</h1>
        {error && <p className="error-message">{error}</p>}
        {Object.entries(usersByPlateform).map(([platformeName, platformUsers]) => (
            <div key={platformeName} className="platform-group">
              <h2>{platformeName}</h2>
              <table>
                <thead>
                <tr>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Compte Activé</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {platformUsers.map((user) => (
                    <tr key={user.id}>
                      <td onClick={() => handleUserClick(user.id)}>{user.username}</td>
                      <td>{user.role}</td>
                      <td>{user.enabled ? "Oui" : "Non"}</td>
                      <td>
                        {isAuthenticated.role === "ADMIN" && (
                            <>
                              <EditButton onClick={() => handleEditUser(user.id)} />
                              <DeleteButton onClick={() => handleDeleteUser(user.id)} />
                              {(!user.enabled) && (
                                <RenewButton onClick={() => handleRenewActivationCode(user.id)} />
                              )}
                            </>
                        )}
                      </td>
                    </tr>
                ))}
                </tbody>
              </table>
            </div>
        ))}
      </div>
  );
};

export default Utilisateurs;
