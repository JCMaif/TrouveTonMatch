import React, { useContext, useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext"; 
import "./Layout.css";
import Header from "../shared/Header/Header";
import Footer from "../shared/Footer/Footer";

const Layout = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const { username, role } = isAuthenticated;

  const navigate = useNavigate();

  useEffect(() => {
    if (!username) {
      navigate("/login");
    }
  }, [username, navigate]);

  return (
    <div className="layout" role="main" aria-label="Page principale">
      <Header aria-label="Bandeau de navigation"/>
      <main className="content" aria-label="Contenu principal">
        <Outlet />
      </main>
     <Footer aria-label="Pied de page"/>
    </div>
  );
};

export default Layout;
