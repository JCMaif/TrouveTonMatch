import React, { useState, createContext, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { authService } from "../services/authService";

export const AuthContext = createContext();

export const AuthProvider = (props) => {
  const [isAuthenticated, setIsAuthenticated] = useState({
    username: "",
    role: "",
    id: "",
  });

  const login = (jwtToken) => {
    try {
      console.log("Token reçu :", jwtToken);
      const decodedToken = jwtDecode(jwtToken);
      console.log("Decoded Token: ", decodedToken);
  
      if (decodedToken?.username && decodedToken?.role && decodedToken?.id) {
        setIsAuthenticated({
          username: decodedToken.username,
          role: decodedToken.role,
          id: decodedToken.id,
        });
      } else {
        console.error("Token mal formé ou clés manquantes dans le token.");
      }
    } catch (error) {
      console.error("Erreur lors de la décodage du token JWT :", error);
    }
  };

  const logout = async () => {
    try {
      await authService.logout();
      setIsAuthenticated({
        username: "",
        role: "",
        id: "",
      });
      localStorage.removeItem("jwtToken");
      sessionStorage.removeItem("jwtToken");
    } catch (error) {
      console.error("Échec de la déconnexion :", error);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("jwtToken") || sessionStorage.getItem("jwtToken");
    
    if (token) {
      try {
        const decodedToken = jwtDecode(token);

        if (decodedToken?.username && decodedToken?.role && decodedToken?.id) {
          setIsAuthenticated({
            username: decodedToken.username,
            role: decodedToken.role,
            id: decodedToken.id,
          });
        } else {
          console.error("Token ne contient pas les clés attendues.");
        }
      } catch (error) {
        console.error("Échec de la lecture du token JWT :", error);
        setIsAuthenticated({ username: "", role: "", id: "" });
      }
    }
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {props.children}
    </AuthContext.Provider>
  );
};
