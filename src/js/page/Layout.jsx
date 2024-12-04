import React, { useContext, useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext"; 
import "./Layout.css";
import Header from "./Header/Header";
import Footer from "./Footer/Footer";

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
    <div className="layout">
      <Header />
      <main className="content">
        <Outlet />
      </main>
     <Footer />
    </div>
  );
};

export default Layout;
