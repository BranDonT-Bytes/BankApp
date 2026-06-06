const BASE_URL = "http://localhost:8080"; // change if needed

export async function login(username, password) {
  const res = await fetch(`${BASE_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!res.ok) throw new Error("Login failed");
  return res.json();
}

export async function getTransfers(userId) {
  const res = await fetch(`${BASE_URL}/transfers/${userId}`);
  return res.json();
}

export async function transferFunds(data) {
  const res = await fetch(`${BASE_URL}/transfers`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  return res.json();
}

export async function getCustomers() {
  const res = await fetch(`${BASE_URL}/api/customers`);
  return res.json();
}

export async function createCustomer(data) {
  const res = await fetch(`${BASE_URL}/api/customers`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  return res.json();
}

export async function deleteCustomer(id) {
  const res = await fetch(`${BASE_URL}/api/customers/${id}`, {
    method: "DELETE",
  });

  return res.json();
}