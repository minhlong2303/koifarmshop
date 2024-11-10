import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CartHistory = () => {
  const [carts, setCarts] = useState([]); // Mặc định là một mảng rỗng
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Lấy dữ liệu các giỏ hàng
    const fetchCarts = async () => {
      try {
        const response = await axios.get('/api/order'); // Gọi API để lấy danh sách giỏ hàng
        // Đảm bảo response.data là một mảng
        setCarts(Array.isArray(response.data) ? response.data : []);
      } catch (error) {
        setError("Không thể tải giỏ hàng!");
      } finally {
        setLoading(false);
      }
    };

    fetchCarts();
  }, []); // Chỉ gọi một lần khi component được render lần đầu tiên

  if (loading) return <p>Đang tải...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>Lịch sử giỏ hàng</h1>
      {carts.length === 0 ? (
        <p>Không có giỏ hàng nào.</p>
      ) : (
        <div>
          {carts.map((cart) => (
            <CartItem key={cart.id} cart={cart} />
          ))}
        </div>
      )}
    </div>
  );
};

const CartItem = ({ cart }) => {
  return (
    <div style={styles.cartItem}>
      <h2>Mã giỏ hàng: {cart.id}</h2>
      <p>Ngày tạo: {new Date(cart.date).toLocaleDateString()}</p>
      <p>Trạng thái: {getStatusLabel(cart.status)}</p>
      <p>Tổng tiền: {cart.total.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>

      <h3>Chi tiết giỏ hàng</h3>
      <ul>
        {cart.orderDetails.map((detail) => (
          <li key={detail.id} style={styles.cartDetail}>
            <span>Cá Koi ID: {detail.koi.koiId}</span>
            <span>Số lượng: {detail.quantity}</span>
            <span>Giá: {detail.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</span>
          </li>
        ))}
      </ul>
    </div>
  );
};

const getStatusLabel = (status) => {
  switch (status) {
    case 'PENDING':
      return 'Đang chờ xử lý';
    case 'CONFIRMED':
      return 'Đã xác nhận';
    case 'SHIPPED':
      return 'Đang giao hàng';
    case 'DELIVERED':
      return 'Đã giao hàng';
    case 'CANCELLED':
      return 'Đã hủy';
    default:
      return 'Không xác định';
  }
};

const styles = {
  cartItem: {
    padding: '20px',
    margin: '10px 0',
    border: '1px solid #ccc',
    borderRadius: '8px',
  },
  cartDetail: {
    display: 'flex',
    justifyContent: 'space-between',
    margin: '5px 0',
  },
};

export default CartHistory;