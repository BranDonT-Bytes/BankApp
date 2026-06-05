import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Admin() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [checking, setChecking] = useState("");
  const [savings, setSavings] = useState("");

  const [deleteUser, setDeleteUser] = useState("");
  const [message, setMessage] = useState("");

  const createCustomer = async () => {
    try {
      await axios.post("http://localhost:8080/api/customers", {
        username,
        password,
        checkingBalance: Number(checking) || 0,
        savingsBalance: Number(savings) || 0,
      });

      setMessage("Customer created successfully");
      setUsername("");
      setPassword("");
      setChecking("");
      setSavings("");
    } catch (err) {
      setMessage("Failed to create customer");
    }
  };

  const deleteCustomer = async () => {
    try {
      await axios.delete(
        `http://localhost:8080/api/customers/username/${deleteUser}`
      );

      setMessage(`Deleted user: ${deleteUser}`);
      setDeleteUser("");
    } catch (err) {
      setMessage("User not found or delete failed");
    }
  };

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="center" style={{ padding: "40px" }}>
      <div className="card" style={{ width: "600px" }}>

        <h2>🛠 Admin Dashboard - BranDonBankerBank</h2>

        {/* CREATE USER */}
        <div className="card" style={{ marginTop: "20px" }}>
          <h3>Create Customer</h3>

          <input placeholder="username" value={username} onChange={(e) => setUsername(e.target.value)} />
          <input placeholder="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <input placeholder="checking balance" value={checking} onChange={(e) => setChecking(e.target.value)} />
          <input placeholder="savings balance" value={savings} onChange={(e) => setSavings(e.target.value)} />

          <div style={{ marginTop: "10px" }}>
            <button onClick={createCustomer}>Create</button>
          </div>
        </div>

        {/* DELETE USER */}
        <div className="card" style={{ marginTop: "20px" }}>
          <h3>Delete Customer</h3>

          <input
            placeholder="username"
            value={deleteUser}
            onChange={(e) => setDeleteUser(e.target.value)}
          />

          <div style={{ marginTop: "10px" }}>
            <button style={{ background: "red" }} onClick={deleteCustomer}>
              Delete
            </button>
          </div>
        </div>

        {/* MESSAGE */}
        {message && <p className="error">{message}</p>}

        {/* LOGOUT */}
        <div style={{ marginTop: "20px" }}>
          <button style={{ background: "black" }} onClick={logout}>
            Logout
          </button>
        </div>

      </div>
    </div>
  );
}