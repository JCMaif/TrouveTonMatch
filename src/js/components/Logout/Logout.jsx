import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import "./Logout.scss";

const Logout = () => {
  const { logout } = useContext(AuthContext);

  const handleLogout = async () => {
    try {
      await logout();
      alert("Vous êtes déconnecté.");
    } catch (error) {
      console.error("Erreur lors de la déconnexion", error);
    }
  };

  return (
    <button onClick={handleLogout} className="logout-btn">
      Logout
    </button>
  );
};

export default Logout;
