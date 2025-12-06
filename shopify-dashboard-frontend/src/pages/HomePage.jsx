function HomePage() {
  return (
    <div className="page-content">
      <h1>Welcome to Shopify Analytics</h1>
      <p style={{ fontSize: "18px", marginTop: "10px", color: "#4b5563" }}>
        View real-time insights, customers, orders and product performance.
      </p>

      <div style={{ marginTop: "40px", display: "grid", gap: "20px", gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))" }}>
        <a href="/dashboard" className="stat-card" style={{ textDecoration: "none", color: "inherit" }}>
          <h3>Dashboard</h3>
          <p className="stat-value">📊</p>
        </a>
        <a href="/orders" className="stat-card" style={{ textDecoration: "none", color: "inherit" }}>
          <h3>Recent Orders</h3>
          <p className="stat-value">🧾</p>
        </a>
        <a href="/customers" className="stat-card" style={{ textDecoration: "none", color: "inherit" }}>
          <h3>Top Customers</h3>
          <p className="stat-value">👥</p>
        </a>
      </div>
    </div>
  );
}

export default HomePage;
