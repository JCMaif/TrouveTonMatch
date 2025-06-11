import {useState, useEffect, useContext} from 'react';
import {AuthContext} from "../../context/AuthContext";
import {Link, useNavigate} from "react-router-dom";
import {userService} from '../../services/services';
import '../../styles/page.scss';
import RenewButton from "../../components/common/buttons/RenewButton/RenewButton.jsx";
import {API_BASE_URL} from "../../config/config.js";
import {FaRegUser} from "react-icons/fa";

const UserList = ({role, title}) => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');
    const {isAuthenticated} = useContext(AuthContext);
    const navigate = useNavigate();


    useEffect(() => {
        if (!isAuthenticated?.token) return;

        const fetchUsers = async () => {
            try {
                let data = await userService.getUsersByRole(role, isAuthenticated.token);
                if (isAuthenticated.role === "PORTEUR" && role === "PARRAIN") {
                    data = data.filter(user => user.isActive);
                }
                setUsers(data);
                console.log("data filtré utilisateurs : ", data);
            } catch (err) {
                setError(`Échec de la récupération des ${title}.`);
            }
        };

        fetchUsers();
    }, [isAuthenticated?.token, role, isAuthenticated.role, title]);

    const handleUserClick = (userId) => {
        navigate(`/profil/${userId}`);
    };

    function handleRenewActivationCode(id) {
        console.log("TODO : gérer le renouvellement du code d'activation");
    }

    const canCreateUser = isAuthenticated.role === "ADMIN" || isAuthenticated.role === "STAFF";


    return (
        <div className="container">
            <h1>{title}</h1>
            {error && <p className="error-message">{error}</p>}
            {canCreateUser && (
                <li className='create-entity'>
                    <Link to={`/signup/${role}`} className="Nav-link">Créer un {role}</Link>
                </li>
            )}

            <table>
                <thead>
                <tr>
                    <th>{title}</th>
                    {canCreateUser && (
                        <>
                            <th>Renouveler activation</th>
                            {role === "PARRAIN" && (
                                <>
                                    <th>Visibilité</th>
                                    <th>Projets suivis/ max</th>
                                </>
                            )}
                        </>
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
                                            className="pictureProfilePetit col-optionel"
                                            src={`${API_BASE_URL}/uploads/${user.profilePicture}`}
                                            alt="Avatar utilisateur"
                                        />
                                    ) : (
                                        <FaRegUser size={50} color="#ccc"/>
                                    )}
                                </div>
                                {user.firstName} {user.lastName}
                            </div>
                        </td>
                        {(isAuthenticated?.role === "ADMIN" || isAuthenticated?.role === "STAFF") && (
                            <>
                                <td>
                                    {!user.enabled && (
                                        <RenewButton onClick={() => handleRenewActivationCode(user.id)}/>
                                    )}
                                </td>
                                {role === "PARRAIN" && (
                                    <>
                                        <td>{user.isActive ? "Visible" : "En retrait"}</td>
                                        <td>{user.nbrProjetsAffectes} / {user.maxProjects}</td>
                                    </>
                                )}
                            </>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserList;
