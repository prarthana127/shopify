import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom';
import DashboardPage from './pages/DashboardPage';
import RecentOrdersPage from './pages/RecentOrdersPage';
import TopCustomersPage from './pages/TopCustomersPage';
import HomePage from './pages/HomePage';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <nav className="sidebar">
          <h2>Shopify Admin</h2>
          <ul>
            <li><NavLink to="/dashboard">Dashboard</NavLink></li>
            <li><NavLink to="/orders">Recent Orders</NavLink></li>
            <li><NavLink to="/customers">Top Customers</NavLink></li>
          </ul>
        </nav>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route path="/orders" element={<RecentOrdersPage />} />
            <Route path="/customers" element={<TopCustomersPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
