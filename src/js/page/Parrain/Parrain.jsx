import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from "../../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import { userService } from '../../services/userService';
import EditButton from '../../components/common/buttons/EditButton/EditButton';
import DeleteButton from '../../components/common/buttons/DeleteButton/DeleteButton';
import './Parrain.scss';

const Parrain = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await userService.getUsers();
        console.log("Utilisateurs récupérés depuis le back-end : ", data);
        setUsers(data);
      } catch (err) {
        setError('Failed to fetch users');
      }
    };

    fetchUsers();
  }, []);

  const handleUserClick = (userId) => {
    navigate(`/profil/${userId}`); 
  };

  const handleEditUser = (userId) => {
    navigate(`/profil/edit/${userId}`); 
  };

  const handleDeleteUser = async (userId) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await userService.deleteUser(userId);
        setUsers(users.filter(user => user.id !== userId)); 
      } catch (err) {
        setError('Failed to delete user');
      }
    }
  };

  return (
    <div className="users-container">
      <h1>Utilisateurs</h1>
      {error && <p className="error-message">{error}</p>}
      {isAuthenticated.role === 'ADMIN' && (
        <li className='create-user_link'>
          <Link to="/signup" className="Nav-link">Créer un utilisateur</Link>
        </li>
      )}
      <table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td onClick={() => handleUserClick(user.id)}>{user.username}</td>
              <td>{user.role}</td>
              <td>
                {isAuthenticated.role === 'ADMIN' && (
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

export default Parrain;
