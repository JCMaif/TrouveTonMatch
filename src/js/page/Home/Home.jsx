import {useContext, useEffect, useState} from 'react';
import './Home.css';
import {Link, useNavigate} from "react-router-dom";
import {compteRenduService, userService} from "../../services/services.js";
import {useAuthenticatedService} from "../../hook/useAuthenticatedService.js";
import {AuthContext} from "@/context/AuthContext.jsx";

const Home = () => {
    const [compteRendus, setCompteRendus] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [users, setUsers] = useState({});
    const navigate = useNavigate();
    const {findAll} = useAuthenticatedService(compteRenduService);
    const {findParrainByPorteurId, findById} = useAuthenticatedService(userService);
    const {isAuthenticated} = useContext(AuthContext);
    const isAdminOrStaff = isAuthenticated?.role === "ADMIN" || isAuthenticated?.role === "STAFF";
    const isParrain = isAuthenticated?.role === "PARRAIN";
    const isPorteur = isAuthenticated?.role === "PORTEUR";

    const fetchUserInfo = async (userId) => {
        if (!userId || users[userId]) {
            return users[userId];
        }

        try {
            const user = await findById(userId);
            if (user) {
                setUsers(prev => ({
                    ...prev,
                    [userId]: { firstName: user.firstName, lastName: user.lastName }
                }));
                return { firstName: user.firstName, lastName: user.lastName };
            }
            return null;
        } catch (e) {
            console.error(`Erreur lors de la récupération de l'utilisateur ${userId}:`, e);
            return null;
        }
    };

    const enrichCompteRendus = async (data) => {
        return await Promise.all(
            data.map(async cr => {
                try {
                    const parrain = await findParrainByPorteurId(cr.porteurId);
                    const porteur = await fetchUserInfo(cr.porteurId);

                    return {
                        ...cr,
                        parrainId: parrain?.id || null,
                        parrainNom: parrain ? `${parrain.firstName} ${parrain.lastName}` : null,
                        porteurNom: porteur ? `${porteur.firstName} ${porteur.lastName}` : null
                    };
                } catch (e) {
                    console.error(`Erreur lors de l'enrichissement du compte-rendu ${cr.id}:`, e);
                    return {
                        ...cr,
                        parrainId: null,
                        parrainNom: null,
                        porteurNom: null
                    };
                }
            })
        );
    };

    const filterCompteRendus = (data) => {
        if (!isAuthenticated) return [];
        const userRole = isAuthenticated.role;
        switch (userRole) {
            case "ADMIN":
            case "STAFF":
                return data;
            case "PORTEUR":
                return data.filter(cr => cr.porteurId === isAuthenticated.id);
            case "PARRAIN":
                return data.filter(cr => cr.parrainId === isAuthenticated.id);
            default:
                return [];
        }
    };

    useEffect(() => {
        const fetchCR = async () => {
            setError('');
            setLoading(true);
            try {
                const allData = await findAll();
                const enrichedData = await enrichCompteRendus(allData)
                const data = filterCompteRendus(enrichedData);
                setCompteRendus(data);

            } catch (e) {
                console.error("Erreur dans la récupération des compte-rendus : ", e);
                setError("Erreur lors de la récupération des compte-rendus");
            } finally {
                setLoading(false);
            }
        };

        if (isAuthenticated) {
            fetchCR();
        }
    }, [isAuthenticated]);


    const handleClick = (id) => navigate(`/compte-rendu/${id}`);

    return (
        <>
            <div className="container">
                <h1>Compte-rendus</h1>
                {error && <p className="error-message">{error}</p>}
                {isPorteur && (
                    <li className="create-entity">
                        <Link to="/compte-rendu" className="Nav-link">Créer un compte rendu</Link>
                    </li> )}
                {loading ? (
                    <p>Chargement en cours...</p>
                ) : (
                    <table>
                        <thead>
                        <tr>
                            <th>Date</th>
                            {isAdminOrStaff && (
                                <>
                                    <th>Porteur</th>
                                    <th>Parrain</th>
                                </>
                            )}
                            {isParrain && (
                                <th>Porteur</th>
                            )}
                            <th>Sujet</th>
                            <th>Echéance</th>
                        </tr>
                        </thead>
                        <tbody>
                        {compteRendus.map((cr) => (
                            <tr key={cr.id} onClick={() => handleClick(cr.id)}>
                                <td>{cr.dateEchange}</td>
                                {isAdminOrStaff && (
                                    <>
                                        <td>{cr.porteurNom || cr.porteurId}</td>
                                        <td>{cr.parrainNom || cr.parrainId}</td>
                                    </>
                                )}
                                {isParrain && (
                                    <td>{cr.porteurNom}</td>
                                )}
                                <td>{cr.sujets}</td>
                                <td>{cr.prochainRdv}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </>
    );
};

export default Home;
