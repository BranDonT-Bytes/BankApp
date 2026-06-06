import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Admin() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [checking, setChecking] = useState("");
  const [savings, setSavings] = useState("");
  const [customers, setCustomers] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const apiUrl = "http://localhost:8080/api/customers";

  const loadCustomers = async () => {
    setError("");
    try {
      const res = await axios.get(apiUrl);
      setCustomers(res.data);
    } catch (err) {
      setError("Failed to load customer list");
    }
  };

  useEffect(() => {
    loadCustomers();
  }, []);

  const createCustomer = async () => {
    setError("");
    setMessage("");

    try {
      await axios.post(apiUrl, {
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
      await loadCustomers();
    } catch (err) {
      setError(
        err.response?.data?.message ||
          err.response?.data ||
          "Failed to create customer"
      );
    }
  };

  const deleteCustomer = async (customerUsername) => {
    setError("");
    setMessage("");

    try {
      await axios.delete(`${apiUrl}/${customerUsername}`);
      setMessage(`Deleted user: ${customerUsername}`);
      await loadCustomers();
    } catch (err) {
      setError(
        err.response?.data?.message ||
          err.response?.data ||
          "Delete failed"
      );
    }
  };

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="center" style={{ padding: "40px" }}>
      <div className="card" style={{ width: "720px" }}>
        <h2>🛠 Admin Dashboard - BranDonBankerBank</h2>

        <div className="card" style={{ marginTop: "20px" }}>
          <h3>Create Customer</h3>

          <input
            placeholder="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            placeholder="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <input
            placeholder="checking balance"
            value={checking}
            onChange={(e) => setChecking(e.target.value)}
          />
          <input
            placeholder="savings balance"
            value={savings}
            onChange={(e) => setSavings(e.target.value)}
          />

          <div style={{ marginTop: "10px" }}>
            <button onClick={createCustomer}>Create</button>
          </div>
        </div>

        <div className="card" style={{ marginTop: "20px" }}>
          <h3>All Customers</h3>

          {error && <p className="error">{error}</p>}
          {message && <p className="success">{message}</p>}

          <div style={{ overflowX: "auto" }}>
            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
                <tr>
                  <th style={{ textAlign: "left", padding: "8px" }}>Username</th>
                  <th style={{ textAlign: "left", padding: "8px" }}>Checking</th>
                  <th style={{ textAlign: "left", padding: "8px" }}>Savings</th>
                  <th style={{ textAlign: "left", padding: "8px" }}>Role</th>
                  <th style={{ textAlign: "left", padding: "8px" }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {customers.map((customer) => (
                  <tr key={customer.username}>
                    <td style={{ padding: "8px" }}>{customer.username}</td>
                    <td style={{ padding: "8px" }}>${customer.checkingBalance ?? 0}</td>
                    <td style={{ padding: "8px" }}>${customer.savingsBalance ?? 0}</td>
                    <td style={{ padding: "8px" }}>{customer.role}</td>
                    <td style={{ padding: "8px" }}>
                      <button
                        style={{ background: "red", color: "white" }}
                        onClick={() => deleteCustomer(customer.username)}
                        disabled={customer.role === "ADMIN"}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div style={{ marginTop: "20px" }}>
          <button style={{ background: "black", color: "white" }} onClick={logout}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
}
