/**
 * ClothWare API Client
 * Connects the frontend to the Java Spring Boot backend
 */

const API_BASE = 'http://localhost:8080/api';

// ── Token helpers
function getToken() { return localStorage.getItem('clothware_jwt'); }
function setToken(t) { localStorage.setItem('clothware_jwt', t); }
function clearToken() { localStorage.removeItem('clothware_jwt'); localStorage.removeItem('clothware_logged_in'); localStorage.removeItem('clothware_user'); }

// ── Base fetch with JWT
async function apiFetch(path, options = {}) {
  const token = getToken();
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  if (token) headers['Authorization'] = 'Bearer ' + token;

  const res = await fetch(API_BASE + path, { ...options, headers });

  if (res.status === 401) {
    clearToken();
    window.location.href = '../login.html';
    return;
  }
  if (!res.ok) {
    const err = await res.json().catch(() => ({ error: 'Request failed' }));
    throw new Error(err.error || 'Request failed');
  }
  return res.json();
}

// ── Auth
const Auth = {
  async login(email, password) {
    const data = await apiFetch('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password })
    });
    setToken(data.token);
    localStorage.setItem('clothware_logged_in', 'true');
    localStorage.setItem('clothware_user', data.email);
    return data;
  },
  logout() { clearToken(); window.location.href = '../login.html'; }
};

// ── Products
const Products = {
  getAll: () => apiFetch('/products'),
  getById: (id) => apiFetch(`/products/${id}`),
  getLowStock: () => apiFetch('/products/low-stock'),
  create: (p) => apiFetch('/products', { method: 'POST', body: JSON.stringify(p) }),
  update: (id, p) => apiFetch(`/products/${id}`, { method: 'PUT', body: JSON.stringify(p) }),
  delete: (id) => apiFetch(`/products/${id}`, { method: 'DELETE' })
};

// ── Customers
const Customers = {
  getAll: () => apiFetch('/customers'),
  create: (c) => apiFetch('/customers', { method: 'POST', body: JSON.stringify(c) }),
  update: (id, c) => apiFetch(`/customers/${id}`, { method: 'PUT', body: JSON.stringify(c) }),
  delete: (id) => apiFetch(`/customers/${id}`, { method: 'DELETE' })
};

// ── Employees
const Employees = {
  getAll: () => apiFetch('/employees'),
  create: (e) => apiFetch('/employees', { method: 'POST', body: JSON.stringify(e) }),
  update: (id, e) => apiFetch(`/employees/${id}`, { method: 'PUT', body: JSON.stringify(e) }),
  delete: (id) => apiFetch(`/employees/${id}`, { method: 'DELETE' })
};

// ── Orders
const Orders = {
  getAll: () => apiFetch('/orders'),
  getById: (id) => apiFetch(`/orders/${id}`),
  getRecent: () => apiFetch('/orders/recent'),
  generateBill: (b) => apiFetch('/orders/generate-bill', { method: 'POST', body: JSON.stringify(b) }),
  delete: (id) => apiFetch(`/orders/${id}`, { method: 'DELETE' })
};

// ── Dashboard
const Dashboard = {
  getStats: () => apiFetch('/dashboard/stats'),
  getReport: () => apiFetch('/dashboard/report')
};

// ── Settings
const SettingsAPI = {
  get: () => apiFetch('/settings'),
  update: (s) => apiFetch('/settings', { method: 'PUT', body: JSON.stringify(s) })
};
