import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Login.scss";
import { authService } from "../../services/authService";
import InputField from "../../components/common/InputField/InputField";
import Checkbox from "../../components/common/CheckBox/CheckBox";
import SubmitButton from "../../components/common/buttons/SubmitButton/SubmitButton";
import {jwtDecode} from "jwt-decode";

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

      const decoded = jwtDecode(token);

      if (token) {
        login(token);

        if (rememberMe) {
          localStorage.setItem("jwtToken", token);
        }
        sessionStorage.setItem("jwtToken", token);
        if (decoded.enabled === false) {
          console.log("Redirection vers:", `/complete-profile/${decoded.id}/${decoded.role}`);
          navigate(`/complete-profile/${decoded.id}/${decoded.role}`);

        } else {
          navigate("/");
        }
      } else {
        console.error("Aucun token reçu après la connexion.");
      }
    } catch (err) {
      console.error("Username or password incorrect : ", err);
      setError(err.message);
    }
  };


  return (
    <div className="login-container" aria-label="page de connexion">
      <div>
        <h1>Login</h1>
        {error && <p className="error-message" aria-live="assertive" aria-atomic="true">{error}</p>}

        <form onSubmit={handleSubmit} aria-label="formulaire de connexion">
          <InputField
            type="text"
            placeholder="Username"
            value={username}
            onChange={setUsername}
            name="username"
            id="username"
            mandatory
            aria-required="true"
            aria-describedby="username-error"
          />
          {!username && error && (
              <span id="username-error" className="error">
              Username is required.
            </span>
          )}
          <InputField
            type="password"
            placeholder="Password"
            value={password}
            onChange={setPassword}
            name="password"
            id="password"
            mandatory
            aria-required="true"
            aria-describedby="password-error"
          />
          {!password && error && (
              <span id="password-error" className="error">
              Password is required.
            </span>
          )}
          <Checkbox
            checked={rememberMe}
            onChange={() => setRememberMe(!rememberMe)}
            label="Remember me"
            id="remember-me"
          />
          <SubmitButton text="Login" />
        </form>
      </div>
    </div>
  );
};

export default Login;
