import { userService } from "../../services/services";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "../../styles/page.scss";
import EditButton from "@/components/common/buttons/EditButton/EditButton.jsx";
import DeleteButton from "@/components/common/buttons/DeleteButton/DeleteButton.jsx";

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

  return (
      <div className="container">
        <h1>Utilisateurs</h1>
        {error && <p className="error-message">{error}</p>}
        <table>
          <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          {users.map((user) => (
              <tr key={user.id}>
                <td onClick={() => handleUserClick(user.id)}>{user.username}</td>
                <td>{user.role}</td>
                <td>
                  {isAuthenticated.role === "ADMIN" && (
                      <>
                        <EditButton onClick={() => handleEditUser(user.id)} />
                        <DeleteButton onClick={() => handleDeleteUser(user.id)} />
                      </>
                  )}
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
};

export default Utilisateurs;
