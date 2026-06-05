import { createContext, useContext, useState } from "react";
import { login } from "../api/api";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  async function handleLogin(username, password) {
    const data = await login(username, password);
    setUser(data);
    return data;
  }

  function logout() {
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, handleLogin, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);