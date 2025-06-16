import {useContext, useEffect, useState} from 'react';
import './Home.css';
import {Link, useNavigate} from "react-router-dom";
import {compteRenduService} from "../../services/services.js";
import {useAuthenticatedService} from "../../hook/useAuthenticatedService.js";
import {AuthContext} from "../../context/AuthContext.jsx";

const Home = () => {
    const [compteRendus, setCompteRendus] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize] = useState(10);
    const [sortField, setSortField] = useState('dateEchange');
    const [sortDirection, setSortDirection] = useState('DESC');
    const [filters, setFilters] = useState({});

    const navigate = useNavigate();
    const {findAll} = useAuthenticatedService(compteRenduService);
    const {isAuthenticated} = useContext(AuthContext);
    const isAdminOrStaff = isAuthenticated?.role === "ADMIN" || isAuthenticated?.role === "STAFF";
    const isParrain = isAuthenticated?.role === "PARRAIN";

    const fetchCR = async () => {
        setError('');
        setLoading(true);
        try {
            const response = await findAll({
                page: 0,
                size: 1000,
                filters
            }, isAuthenticated.token);
            console.log("cr :", response.content);
            const data = filterCompteRendus(response.content);
            console.log("filteredCr : ", data);
            setCompteRendus(data);
        } catch (e) {
            console.error("Erreur dans la récupération des compte-rendus : ", e);
            setError("Erreur lors de la récupération des compte-rendus");
        } finally {
            setLoading(false);
        }
    };

    const filterCompteRendus = (data) => {
        if (!isAuthenticated) return [];
        switch (isAuthenticated.role) {
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
        if (isAuthenticated) {
            fetchCR();
        }
    }, [isAuthenticated]);

    const handleClick = (id) => navigate(`/compte-rendu/${id}`);

    const handleSort = (field) => {
        const isAsc = sortField === field && sortDirection === 'asc';
        const newOrder = isAsc ? 'desc' : 'asc';

        const sorted = [...compteRendus].sort((a, b) => {
            const aValue = a[field]?.toString().toLowerCase() ?? '';
            const bValue = b[field]?.toString().toLowerCase() ?? '';
            return (aValue < bValue ? -1 : aValue > bValue ? 1 : 0) * (newOrder === 'asc' ? 1 : -1);
        });

        setCompteRendus(sorted);
        setSortField(field);
        setSortDirection(newOrder);
    };

    return (
        <div className="container">
            <h1>Compte-rendus</h1>
            {error && <p className="error-message">{error}</p>}
            {isParrain && (
                <li className="create-entity">
                    <Link to="/compte-rendu" className="Nav-link">Créer un compte rendu</Link>
                </li>
            )}

            {loading ? (
                <p>Chargement en cours...</p>
            ) : (
                <>
                    <table>
                        <thead>
                        <tr>
                            <th onClick={() => handleSort('dateEchange')}>Date ↑↓</th>
                            {isAdminOrStaff && (
                                <>
                                    <th onClick={() => handleSort('porteurLastname')}>Porteur ↑↓</th>
                                    <th onClick={() => handleSort('parrainLastname')}>Parrain ↑↓</th>
                                </>
                            )}
                            {isParrain && (
                                <th onClick={() => handleSort('projetTitle')}>Projet ↑↓</th>
                            )}
                            <th onClick={() => handleSort('sujets')}>Sujet ↑↓</th>
                            <th onClick={() => handleSort('prochainRdv')}>Échéance ↑↓</th>
                        </tr>
                        </thead>
                        <tbody>
                        {compteRendus.map((cr) => (
                            <tr key={cr.id} onClick={() => handleClick(cr.id)}>
                                <td>{cr.dateEchange}</td>
                                {isAdminOrStaff && (
                                    <>
                                        <td>{cr.porteurFirstname} {cr.porteurLastname}</td>
                                        <td>{cr.parrainFirstname} {cr.parrainLastname}</td>
                                    </>
                                )}
                                {isParrain && (
                                    <td>{cr.projetTitle}</td>
                                )}
                                <td>{cr.sujets}</td>
                                <td>{cr.prochainRdv}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <div className="pagination">
                        <button onClick={() => setCurrentPage(p => Math.max(p - 1, 1))} disabled={currentPage === 1}>Précédent</button>
                        <span>Page {currentPage}</span>
                        <button onClick={() => setCurrentPage(p => p + 1)}>Suivant</button>
                    </div>
                </>
            )}
        </div>
    );
};

export default Home;
