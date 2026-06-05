import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleSignup = async () => {
    if (!username || !password) {
      alert("Username and password required");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/customers", {
        username,
        password,
        checkingBalance: 0,
        savingsBalance: 0,
      });

      alert("Account created!");
      navigate("/");
    } catch (err) {
      alert("Signup failed (username may already exist)");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Sign Up</h2>

      <input
        placeholder="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <br /><br />

      <input
        placeholder="password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <br /><br />

      <button onClick={handleSignup}>Create Account</button>
    </div>
  );
}