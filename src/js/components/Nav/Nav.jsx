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
          <Link to="/signup" className="Nav-link">Créer un utilisateur</Link>
        </li>
        <li>
          <Link to={`/profil/${id}`} className="Nav-link">Mon profil</Link>
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
          <Link to={`/profil/${id}`} className="Nav-link">Mon profil</Link>
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
