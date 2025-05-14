import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./page/Login/Login.jsx";
import "./index.scss";
import Home from "./page/Home/Home.jsx";
import Layout from "./layouts/Layout.jsx";
import {AuthProvider} from "./context/AuthContext.jsx";
import Projet from "./page/Projet/Projet.jsx";
import Match from "./page/Match/Match.jsx";
import Signup from "./page/Signup/Signup.jsx";
import UserList from "./page/Utilisateurs/UserList.jsx";
import Plateforme from "./page/Plateforme/Plateforme.jsx";
import PlateformeDetails from "./page/Plateforme/PlateformeDetails.jsx";
import FinalisationProfile from "./page/Profile/FinalisationProfile.jsx";
import UserProfile from "./page/Utilisateurs/UserProfile.jsx";
import ProjetForm from "./page/Projet/ProjetForm.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/projets" element={<Projet />} />
            <Route path="/projet/:projetId" element={<ProjetForm isEditing={false} />} />
            <Route path="/projet/edit/:projetId" element={<ProjetForm isEditing={true} />} />
            <Route path="/creer-projet" element={<ProjetForm isEditing={false} />} />
            <Route path="/matches" element={<Match />} />
            <Route path="/parrains" element={<UserList role="PARRAIN" title="Parrains" />} />
            <Route path="/porteurs" element={<UserList role="PORTEUR" title="Porteurs de projet" />} />
            <Route path="/plateformes" element={<Plateforme />} />
            <Route path="/plateforme/:id" element={<PlateformeDetails />} />
            <Route path="/profil/:userId" element={<UserProfile isEditing={false} />} />
            <Route path="/profil/edit/:userId" element={<UserProfile isEditing={true} />} />
            <Route path="/signup/PARRAIN" element={<Signup role={"PARRAIN"} />} />
            <Route path="/signup/PORTEUR" element={<Signup role={"PORTEUR"} />} />
            <Route path="/complete-profile/:userId/:role" element={<FinalisationProfile />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>
);

