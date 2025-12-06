import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const dashboardAPI = {
  getSummary: (tenantId) =>
    api.get(`/dashboard/summary?tenantId=${tenantId}`),

  getRecentOrders: (tenantId, limit = 10) =>
    api.get(`/dashboard/recent-orders?tenantId=${tenantId}&limit=${limit}`),

  getTopCustomers: (tenantId, limit = 5) =>
    api.get(`/dashboard/top-customers?tenantId=${tenantId}&limit=${limit}`)
};
