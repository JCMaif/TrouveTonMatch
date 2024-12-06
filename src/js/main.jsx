import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./page/Login/Login.jsx";
import "./index.css";
import Home from "./page/Home/Home.jsx";
import Layout from "./layouts/Layout.jsx";
import { AuthProvider } from "./context/AuthContext.jsx";
import Utilisateurs from "./page/Utilisateurs/Utilisateurs.jsx";
import Projet from "./page/Projet/Projet.jsx";
import Match from "./page/Match/Match.jsx";
import Profile from "./page/Profile/Profile.jsx";
import Signup from "./page/Signup/Signup.jsx";
import EditProfile from "./page/EditProfile/EditProfile.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/utilisateurs" element={<Utilisateurs />} />
            <Route path="/projets" element={<Projet />} />
            <Route path="/matches" element={<Match />} />
            <Route path="/profil/:id" element={<Profile />} />
            <Route path="/profil/edit/:userId" element={<EditProfile />} />
            <Route path="/signup" element={<Signup />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>
);

