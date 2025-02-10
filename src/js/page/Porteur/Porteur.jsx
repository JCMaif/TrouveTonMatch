import  { useState, useEffect, useContext } from 'react';
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { userService } from '../../services/services';
import '../../styles/page.scss';
import RenewButton from "../../components/common/buttons/RenewButton/RenewButton.jsx";

const Porteur = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await userService.getUsersByRole('PORTEUR', isAuthenticated.token);
        setUsers(data);
      } catch (err) {
        setError('Failed to fetch users');
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
      <h1>Porteurs de projet</h1>
      {error && <p className="error-message">{error}</p>}
      <table>
        <thead>
        <tr>
          <th>Username</th>
          <th>Renouveller activation</th>
        </tr>
        </thead>
        <tbody>
          {users.map(user => (
              <tr key={user.id}>
                <td onClick={() => handleUserClick(user.id)}>{user.username}</td>
                <td>
                   <span>
                      {(!user.enabled) && (
                          <RenewButton onClick={() => handleRenewActivationCode(user.id)}/>
                      )}
                   </span>
                </td>
              </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Porteur;
