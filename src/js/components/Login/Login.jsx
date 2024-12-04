import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

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
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        const { token } = data;
        login(token);

        if (rememberMe) {
          localStorage.setItem("jwtToken", token);
        }
        sessionStorage.setItem("jwtToken", token);
        navigate("/");
      } else {
        const errData = await response.json();
        setError(errData.message || "Login failed");
      }
    } catch (err) {
      console.error("Login failed:", err);
      alert("An error occurred during login.");
    }
  };

  return (
    <div className="login-container">
      <div>
        <h1>Login</h1>
        {error && <p className="error-message">{error}</p>}

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <div>
            <input
              type="checkbox"
              checked={rememberMe}
              onChange={() => setRememberMe(!rememberMe)}
            />
            <label>Remember me</label>
          </div>

          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
