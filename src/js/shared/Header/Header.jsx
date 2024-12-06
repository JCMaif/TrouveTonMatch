import React, { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import "./Header.scss";
import Nav from "../../components/Nav/Nav";
import Logout from "../../components/Logout/Logout";

const Header = () => {
  return (
    <header className="header">
      <div className="logo" title="Initiative Deux-Sèvres">
        <img src="/logo.png" alt="logo" />
      </div>
      <nav className="nav">
        <ul>
          <Nav />
        </ul>
      </nav>
      <div className="user-info">
        <Logout />
      </div>
    </header>
  );
};

export default Header;
