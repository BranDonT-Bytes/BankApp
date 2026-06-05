import { useState } from "react";

export default function Transfer() {
  const [amount, setAmount] = useState("");
  const [toUser, setToUser] = useState("");

  return (
    <div>
      <h2>Transfer Money</h2>

      <input
        placeholder="To user"
        value={toUser}
        onChange={(e) => setToUser(e.target.value)}
      />

      <input
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      <button>Send</button>
    </div>
  );
}