import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav style={{ padding: "10px", display: "flex", gap: "10px" }}>
      <Link to="/dashboard">Dashboard</Link>
      <Link to="/transfers">Transfers</Link>
      <Link to="/admin">Admin</Link>
      <Link to="/login">Login</Link>
    </nav>
  );
}