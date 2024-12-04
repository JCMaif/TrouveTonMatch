import React, { useState, createContext, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();


export const AuthProvider = (props) => {

  const [isAuthenticated, setIsAuthenticated] = useState({
    username: "",
    role: "",
  });

  const login = (jwtToken) => {
    const decodedToken = jwtDecode(jwtToken);
    setIsAuthenticated({
      username: decodedToken.username,
      role: decodedToken.role,
    });
  };

  const logout = async () => {
    try {
      await fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
        },
      });
      setIsAuthenticated({
        username: "",
        role: "",
      });
      localStorage.removeItem("jwtToken");
      sessionStorage.removeItem("jwtToken");
    //   navigate("/login");
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {props.children}
    </AuthContext.Provider>
  );
};
