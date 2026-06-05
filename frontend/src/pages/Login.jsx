import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {
    setError("");

    try {
      const res = await axios.post("http://localhost:8080/auth/login", {
        username,
        password,
      });

      const user = res.data;

      localStorage.setItem("user", JSON.stringify(user));

      const isAdmin = user.role === "ADMIN";

      if (isAdmin) {
        navigate("/admin");
      } else {
        navigate("/dashboard");
      }

    } catch (err) {
      setError(err.response?.data || "Invalid username or password");
    }
  };

  return (
    <div className="center" style={{ height: "100vh" }}>
      <div className="card" style={{ width: "320px", textAlign: "center" }}>
        <h2>BranDonBankerBank Login</h2>

        <input
          placeholder="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin} style={{ marginTop: "10px" }}>
          Login
        </button>

        <button
          style={{ marginTop: "10px", background: "#555" }}
          onClick={() => navigate("/signup")}
        >
          Create Account
        </button>

        {error && <div className="error">{error}</div>}
      </div>
    </div>
  );
}