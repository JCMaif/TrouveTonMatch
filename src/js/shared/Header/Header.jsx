import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Header.scss";

const Header = () => {
  const { isAuthenticated, logout } = useContext(AuthContext);
  const { username, role } = isAuthenticated;

  const adminLinks = (
    <>
      <li>
        <Link to="/utilisateurs">Utilisateurs</Link>
      </li>
      <li>
        <Link to="/projets">Projets</Link>
      </li>
      <li>
        <Link to="/matches">Matches</Link>
      </li>
      <li>
        <Link to={`/utilisateurs/${username}`}>Mon profil</Link>
      </li>
    </>
  );

  const userLinks = (
    <>
      <li>
        <Link to="/utilisateurs">Utilisateurs</Link>
      </li>
      <li>
        <Link to="/projets">Projets</Link>
      </li>
      <li>
        <Link to={`/utilisateurs/${username}`}>Mon profil</Link>
      </li>
    </>
  );

  return (
    <header className="header">
      <div className="logo" title="Initiative Deux-Sèvres">
        <img src="/logo.png" alt="logo" />
      </div>
      <nav className="nav">
        <ul>
          {isAuthenticated && role === "ADMIN" && adminLinks}
          {isAuthenticated && role === "USER" && userLinks}
          {!isAuthenticated && (
            <li>
              <Link to="/login">Login</Link>
            </li>
          )}
        </ul>
      </nav>

      <div className="user-info">
        {/* <span>Bienvenue, {username} </span>
        <span>{role}</span> */}
        <button onClick={logout}>Logout</button>
      </div>
    </header>
  );
};

export default Header;
