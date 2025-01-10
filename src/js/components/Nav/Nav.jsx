import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

const Nav = () => {
  const { isAuthenticated, logout } = useContext(AuthContext);
  const { username, role, id } = isAuthenticated;

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
      </>
    );
  }

  if (isAuthenticated && role === "USER") {
    return (
      <>
        <li>
          <Link to="/utilisateurs" className="Nav-link">Utilisateurs</Link>
        </li>
        <li>
          <Link to="/projets" className="Nav-link">Projets</Link>
        </li>
        <li>
          <Link to={`/profil/${id}`} className="Nav-link">Mon espace</Link>
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
        </>
      );
  }
  if (isAuthenticated && role === "PLATEFORME") {
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
          <Link to="/matches" className="Nav-link">Matches</Link>
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
