import { useState, useEffect } from 'react';
import { dashboardAPI } from '../services/api';

function TopCustomersPage() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    dashboardAPI.getTopCustomers('tenant1', 5)
      .then(response => {
        setCustomers(response.data.customers || []);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <div className="loading">Loading customers...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="page-content">
      <h1>Top Customers</h1>
      <div className="customers-list">
        {customers.length === 0 ? (
          <p className="no-data">No customers found</p>
        ) : (
          customers.map((customer, idx) => (
            <div key={idx} className="customer-card">
              <div>
                <p className="customer-email">{customer.email}</p>
                <p className="customer-orders">{customer.orderCount} orders</p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default TopCustomersPage;
