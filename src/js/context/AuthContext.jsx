import { useState, createContext, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { authService } from "../services/authService";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
  const [isAuthenticated, setIsAuthenticated] = useState({
    username: "",
    role: "",
    id: "",
    token: "",
    plateformeId: "",
    enabled: undefined,
  });
  useEffect(() => {
    initializeAuth();
  }, []);

  const saveToken = (token, rememberMe = false) => {
    const storage = rememberMe ? localStorage : sessionStorage;
    storage.setItem("jwtToken", token);
  };

  const clearToken = () => {
    localStorage.removeItem("jwtToken");
    sessionStorage.removeItem("jwtToken");
  };


  const login = (jwtToken, rememberMe= false) => {
    try {
      //console.log("Token reçu :", jwtToken);
      const decodedToken = jwtDecode(jwtToken);
      // console.log("Decoded Token: ", decodedToken);
      const currentTime = Date.now() / 1000;

      if (decodedToken.exp < currentTime) {
        console.warn("⚠️ Token expiré.");
        clearToken();
        return;
      }

      if (decodedToken?.username && decodedToken?.role && decodedToken?.id) {
        setIsAuthenticated({
          username: decodedToken.username,
          role: decodedToken.role,
          id: decodedToken.id,
          token: jwtToken,
          enabled: decodedToken.enabled,
          plateformeId: decodedToken.plateformeId,
        });
        saveToken(jwtToken, rememberMe);
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
    } catch (error) {
      console.error("Échec de la déconnexion :", error);
    } finally {
      setIsAuthenticated({
        username: "",
        role: "",
        id: "",
        token: "",
        plateformeId: "",
        enabled: undefined,
      });
      clearToken();
    }
  };

  const initializeAuth = () => {
    const token = localStorage.getItem("jwtToken") || sessionStorage.getItem("jwtToken");

    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        const currentTime = Date.now() / 1000;

        if (decodedToken.exp < currentTime) {
          console.warn("Token expiré.");
          clearToken();
        } else {
          setIsAuthenticated({
            username: decodedToken.username,
            role: decodedToken.role,
            id: decodedToken.id,
            plateformeId: decodedToken.plateformeId,
            token,
            enabled: decodedToken.enabled ?? undefined,
          });
        }
      } catch (error) {
        console.error("Échec de la lecture ou du décodage du token JWT :", error);
        clearToken();
      }
    }
  };


  useEffect(() => {
    initializeAuth();
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
