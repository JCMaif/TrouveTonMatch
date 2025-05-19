import React, {useContext, useEffect, useState} from 'react';
import './Home.css';
import {Link} from "react-router-dom";
import {AuthContext} from "../../context/AuthContext.jsx";
import {compteRenduService} from "../../services/services.js";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";

const Home = () => {
    const {isAuthenticated} = useContext(AuthContext);
    const [compterendus, setCompterendus] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const { findAll } = useAuthenticatedService(compteRenduService);

    useEffect(() => {
        const fetchCR = async () => {
            setError('');
            setLoading(true);
            try {
                const compterendus = await findAll(isAuthenticated.token);
                console.log("compte-rendus :", compterendus);
                setCompterendus(compterendus);
            } catch (e) {
                console.error("Erreur dans la récupération des compte-rendus : ", e);
                setError("Erreur lors de la récupération des compte-rendus")
            } finally {
                setLoading(false);
            }
            fetchCR();
        }
    }, []);

    return (
        <>
            <h1>Compte-rendus</h1>
            <div className="container">
                <li className="create-entity">
                    <Link to="/compte-rendu" className="Nav-link">Créer un compte rendu</Link>
                </li>
                <table>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Sujet</th>
                        <th>Echéance</th>
                    </tr>
                    </thead>
                    <tbody>
                    {compterendus.map((cr) => (
                        <tr key={cr.id}>
                            <td>{cr.dateEchange}</td>
                            <td>{cr.sujets}</td>
                            <td>{cr.prochainRdv}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                {error && <p className="error-message">{error}</p>}
            </div>
        </>
    );
};

export default Home;
