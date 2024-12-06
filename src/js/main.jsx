import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./page/Login/Login.jsx";
import "./index.css";
import Home from "./page/Home/Home.jsx";
import Layout from "./layouts/Layout.jsx";
import { AuthProvider } from "./context/AuthContext.jsx";
import Utilisateurs from "./page/Utilisateurs/Utilisateurs.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/utilisateurs" element={<Utilisateurs />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>
);

