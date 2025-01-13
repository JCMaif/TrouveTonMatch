import {useContext, useEffect, useState} from "react";
import '../../styles/page.scss';
import { plateformeService } from "../../services/services";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Plateforme = () => {

    const [plateformes, setPlateformes] = useState([]);
    const [error, setError] = useState(null);
    const {isAuthenticated} = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPlateformes = async () => {
            try {
                const response = await plateformeService.findAll(isAuthenticated.token);
                setPlateformes(response);
            } catch (err) {
                console.log(err);
                setError('Failed to fetch plateformes');
            }
        };
        fetchPlateformes();
    }, [isAuthenticated.token]);

    const handleClick = (plateformeId) => {
        navigate('/plateforme/' + plateformeId);
    }

    return (
        <div className="container">
            <h1>Plateformes</h1>
            {error && <p className="error-message">{error}</p>}
            <table>
                <thead>
                <tr>
                    <th>Plateforme</th>
                </tr>
                </thead>
                <tbody>
                {plateformes.map((plateforme) => (
                    <tr key={plateforme.id} onClick={() => handleClick(plateforme.id)}>
                        <td>{plateforme.nom}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Plateforme;