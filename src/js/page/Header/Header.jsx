import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';

const Header = () => {
    const { isAuthenticated, logout } = useContext(AuthContext);
    const { username, role } = isAuthenticated;

    return (
        <header className="header">
            <div className="logo" title='Initiative Deux-Sèvres'>
                <img src="public/logo.png" alt="logo" /></div>
            <nav className="nav">
                <ul>
                    <li>
                        {isAuthenticated ? (
                            <Link to="/">Home</Link>
                        ) : (
                            <Link to="/login">Login</Link>
                        )}
                    </li>
                </ul>
            </nav>
            <div className="user-info">
          <span>Bienvenue, {username}</span>
          <span>Rôle: {role}</span>
          <button onClick={logout}>Logout</button>
        </div>
        </header>
    );
};

export default Header;