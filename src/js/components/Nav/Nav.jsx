import  {useContext, useEffect, useState} from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import {plateformeService} from "../../services/services.js";

const Nav = () => {
  const { isAuthenticated} = useContext(AuthContext);
  const { role, id, plateformeId } = isAuthenticated;
  const [ plateformeNom, setPlateformeNom ] = useState("");

  useEffect(() => {
      const fetchPlateformeNom = async () => {
          if (plateformeId) {
              try {
                  const response = await plateformeService.findById(plateformeId, isAuthenticated.token);
                  setPlateformeNom(response.nom);
              } catch (err) {
                  console.log(err);
                  setPlateformeNom("Inconnu")
              }
          }
      }
      fetchPlateformeNom();
  }, [plateformeId, isAuthenticated.token]);


  if (isAuthenticated && role === "ADMIN") {
    return (
        <>
            <li>
                <Link to="/utilisateurs" className="Nav-link">Utilisateurs</Link>
            </li>
            <li>
                <Link to="/projets" className="Nav-link">Projets</Link>
            </li>
            <li>
                <Link to="/matches" className="Nav-link">Matches</Link>
            </li>
            <li>
                <Link to="/plateformes" className="Nav-link">Plateformes</Link>
            </li>
            <li>
                <Link to="/documents" className="Nav-link">Bibliothèque</Link>
            </li>
        </>
    );
  }

    if (isAuthenticated && role === "PORTEUR") {
        return (
            <>
                <li>
                <Link to="/projets" className="Nav-link">Projets</Link>
            </li>
            <li>
                <Link to="/parrains" className="Nav-link">Parrains</Link>
            </li>
            <li>
                <Link to={`/profil/${id}`} className="Nav-link">Mon espace</Link>
            </li>
            <li>
                <Link to="/documents" className="Nav-link">Bibliothèque</Link>
            </li>
        </>
    );
    }
    if (isAuthenticated && role === "PARRAIN") {
        return (
            <>
            <li>
                  <Link to="/projets" className="Nav-link">Projets</Link>
              </li>
              <li>
                  <Link to={`/profil/${id}`} className="Nav-link">Mon espace</Link>
              </li>
              <li>
                  <Link to="/documents" className="Nav-link">Bibliothèque</Link>
              </li>
          </>
      );
    }
    if (isAuthenticated && role === "STAFF") {
        return (
            <>
            <li>
                    <Link to="/parrains" className="Nav-link">Parrains</Link>
                </li>
                <li>
                    <Link to="/porteurs" className="Nav-link">Porteurs</Link>
                </li>
                <li>
                    <Link to="/projets" className="Nav-link">Projets</Link>
                </li>
                <li>
                    <Link to="/documents" className="Nav-link">Bibliothèque</Link>
                </li>
            </>
        );
    }
  
  if (!isAuthenticated) {
    return (
      <li>
        <Link to="/login" className="Nav-link">Login</Link>
      </li>
    );
  }

  return null;
};

export default Nav;
