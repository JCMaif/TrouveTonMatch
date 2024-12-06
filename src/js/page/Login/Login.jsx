import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Login.scss";
import { authService } from "../../services/authService";
import InputField from "../../components/common/InputField/InputField";
import Checkbox from "../../components/common/CheckBox/CheckBox";
import SubmitButton from "../../components/common/buttons/SubmitButton/SubmitButton";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [rememberMe, setRememberMe] = useState(false);
  const [error, setError] = useState("");
  const { login } = useContext(AuthContext);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await authService.login(username, password); 
      const { token } = data;
      console.log("Token : ", token);
  
      if (token) {
        login(token); 
        if (rememberMe) {
          localStorage.setItem("jwtToken", token);
        }
        sessionStorage.setItem("jwtToken", token);
        navigate("/");
      } else {
        console.error("Aucun token reçu après la connexion.");
      }
    } catch (err) {
      console.error("Erreur dans la soumission du formulaire de connexion : ", err);
      setError(err.message);
    }
  };
  

  return (
    <div className="login-container">
      <div>
        <h1>Login</h1>
        {error && <p className="error-message">{error}</p>}

        <form onSubmit={handleSubmit}>
          <InputField
            type="text"
            placeholder="Username"
            value={username}
            onChange={setUsername}
            name="username"
            id="username"
            mandatory
          />
          <InputField
            type="password"
            placeholder="Password"
            value={password}
            onChange={setPassword}
            name="password"
            id="password"
            mandatory
          />
          <Checkbox
            checked={rememberMe}
            onChange={() => setRememberMe(!rememberMe)}
            label="Remember me"
          />
          <SubmitButton text="Login" />
        </form>
      </div>
    </div>
  );
};

export default Login;
