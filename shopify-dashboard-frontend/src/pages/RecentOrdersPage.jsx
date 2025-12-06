import { useState, useEffect } from 'react';
import { dashboardAPI } from '../services/api';

function RecentOrdersPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    dashboardAPI.getRecentOrders('tenant1', 10)
      .then(response => {
        setOrders(response.data.orders || []);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <div className="loading">Loading orders...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="page-content">
      <h1>Recent Orders</h1>
      <div className="orders-list">
        {orders.length === 0 ? (
          <p className="no-data">No orders found</p>
        ) : (
          orders.map(order => (
            <div key={order.id} className="order-card">
              <div>
                <p><strong>Order ID:</strong> {order.shopifyOrderId || order.id}</p>
                <p><strong>Customer:</strong> {order.customerEmail || 'N/A'}</p>
              </div>
              <div>
                <p><strong>Total:</strong> ${order.totalPrice}</p>
                <p><strong>Status:</strong> {order.fulfillmentStatus || 'Pending'}</p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default RecentOrdersPage;
