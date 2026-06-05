import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const user = JSON.parse(localStorage.getItem("user"));
  const navigate = useNavigate();

  const [data, setData] = useState(user);
  const [amount, setAmount] = useState("");
  const [error, setError] = useState("");

  const username = user?.username;

  const update = (res) => {
    setData(res.data);
    localStorage.setItem("user", JSON.stringify(res.data));
  };

  
  const validate = (val) => {
    if (val === "" || val === null) return "Enter an amount";

    if (!/^\d+(\.\d+)?$/.test(val)) {
      return "Only numeric values allowed";
    }

    if (Number(val) <= 0) return "Amount must be greater than 0";

    return null;
  };

  const call = async (url) => {
    setError("");

    const validationError = validate(amount);
    if (validationError) {
      setError(validationError);
      return;
    }

    try {
      const res = await axios.post(url, {
        username,
        amount: Number(amount),
      });

      update(res);
      setAmount("");

    } catch (e) {
        const backendMessage =
          e.response?.data?.message ||
          e.response?.data?.error ||
          (typeof e.response?.data === "string" ? e.response.data : null);
      
        setError(backendMessage || e.message || "Transaction failed");
      }
  };

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  if (!data) return <div>Loading...</div>;

  return (
    <div className="center" style={{ padding: "40px" }}>
      <div style={{ width: "800px" }}>

        <h2>Welcome, {data.username} — BranDonBankerBank</h2>

        {/* ACCOUNTS */}
        <div className="row">
          <div className="card" style={{ flex: 1, textAlign: "center" }}>
            <h3>Checking</h3>
            <h2>${data.checkingBalance}</h2>
          </div>

          <div className="card" style={{ flex: 1, textAlign: "center" }}>
            <h3>Savings</h3>
            <h2>${data.savingsBalance}</h2>
          </div>
        </div>

        {/* ACTIONS */}
        <div className="card" style={{ marginTop: "20px" }}>
          <input
            placeholder="Enter amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />

          <div className="row" style={{ marginTop: "10px" }}>
            <button onClick={() =>
              call("http://localhost:8080/api/customers/checking/deposit")
            }>
              Deposit Checking
            </button>

            <button onClick={() =>
              call("http://localhost:8080/api/customers/checking/withdraw")
            }>
              Withdraw Checking
            </button>

            <button onClick={() =>
              call("http://localhost:8080/api/customers/savings/deposit")
            }>
              Deposit Savings
            </button>

            <button onClick={() =>
              call("http://localhost:8080/api/customers/savings/withdraw")
            }>
              Withdraw Savings
            </button>
          </div>

          <div className="row" style={{ marginTop: "10px" }}>
            <button onClick={() =>
              call("http://localhost:8080/api/customers/transfer/checking-to-savings")
            }>
              Checking → Savings
            </button>

            <button onClick={() =>
              call("http://localhost:8080/api/customers/transfer/savings-to-checking")
            }>
              Savings → Checking
            </button>
          </div>

          {error && <div className="error">{error}</div>}
        </div>

        <div style={{ marginTop: "20px" }}>
          <button style={{ background: "red" }} onClick={logout}>
            Logout
          </button>
        </div>

      </div>
    </div>
  );
}