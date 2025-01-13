import  { useState, useEffect, useContext } from 'react';
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { userService } from '../../services/services';
import '../../styles/page.scss';

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

  return (
    <div className="container">
      <h1>Porteurs de projet</h1>
      {error && <p className="error-message">{error}</p>}
      <table>
        <thead>
          <tr>
            <th>Username</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td onClick={() => handleUserClick(user.id)}>{user.username}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Porteur;
