import React, { useState, useEffect } from "react";
import axios from "axios";
import { Alert, Button, Form, Input, Modal, Rate, Table, Tag } from "antd";
import { toast } from "react-toastify";
import api from "../../../config/axios";
import { useForm } from "antd/es/form/Form";

const CartHistory = () => {
  const [orders, setOrders] = useState([]);
  const [form] = useForm();
  const [selectedOrder, setSelectedOrder] = useState(null);

  // Fetch order history
  const fetchHistory = async () => {
    try {
      const response = await api.get("/order");
      setOrders(response.data);
    } catch (error) {
      console.log(error);
      toast.error("Failed to load order history");
    }
  };

  // Define columns for the table
  const columns = [
    {
      title: "Ngày đặt hàng",
      dataIndex: "date",
      key: "date",
      render: (date) => new Date(date).toLocaleDateString(),
    },
    {
      title: "Tổng tiền",
      dataIndex: "total",
      key: "total",
      render: (total) => `${total.toLocaleString()} VND`,
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (status) => (
        <Tag color={status === "PAID" ? "green" : "orange"}>{status}</Tag>
      ),
    },
    {
      title: "Chi tiết đơn hàng",
      dataIndex: "orderDetails",
      key: "orderDetails",
      render: (orderDetails) =>
        orderDetails.map((item) => (
          <div key={item.id} style={{ marginBottom: "10px" }}>
            <strong>Price: {item.price.toLocaleString()} VND</strong> x{" "}
            {item.quantity}
          </div>
        )),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "payment",
      key: "payment",
      render: (payment) => (payment ? payment.payment_method : "Not Paid"),
    },
    {
      title: "Đánh giá",
      dataIndex: "rating",
      key: "rating",
      render: (feedback, record) => (
        <Rate disabled defaultValue={record?.rating} />
      ),
    },
    {
      title: "Phản hồi",
      dataIndex: "feedback",
      key: "feedback",
      render: (feedback, record) =>
        feedback ? feedback.content : "No Feedback",
    },
    {
      title: "Hành động",
      dataIndex: "id",
      key: "action",
      render: (id, record) => (
        <Button
          onClick={() => {
            setSelectedOrder(record); // Select order to send feedback
          }}
          type="primary"
        >
          Đánh giá
        </Button>
      ),
    },
  ];

  // Fetch history when component loads
  useEffect(() => {
    fetchHistory();
  }, []);

  // Handle feedback form submission
  const handleFeedback = async (values) => {
    if (!selectedOrder) return;

    values.orderID = selectedOrder.id; // Include order ID in feedback request
    try {
      // Post feedback to server
      await api.post("/feedback", values);

      // Update the selected order with new feedback data locally
      const updatedOrders = orders.map((order) =>
        order.id === selectedOrder.id
          ? {
              ...order,
              feedback: { content: values.content }, // Update feedback content
              rating: values.rating, // Update rating
            }
          : order
      );

      setOrders(updatedOrders); // Update the order list with the modified order
      setSelectedOrder(null); // Close feedback modal
      form.resetFields(); // Reset form fields
      toast.success("Successfully created feedback");
    } catch (error) {
      console.log(error);
      toast.error("Failed to submit feedback");
    }
  };

  return (
    <div className="history">
      {/* Display order history */}
      <Table dataSource={orders} columns={columns} rowKey="id" />

      {/* Feedback modal */}
      <Modal
        title="Phản hồi"
        open={!!selectedOrder} // Modal is open when there's a selected order
        onOk={() => form.submit()}
        onCancel={() => setSelectedOrder(null)} // Close modal on cancel
      >
        <Alert
          message={`Feedback for order #${selectedOrder?.id}`}
          type="info"
        />
        <Form labelCol={{ span: 24 }} onFinish={handleFeedback} form={form}>
          <Form.Item
            label="Đánh giá"
            name="rating"
            rules={[
              {
                required: true,
                message: "Please rate the order",
              },
            ]}
          >
            <Rate />
          </Form.Item>
          <Form.Item
            label="Phản hồi"
            name="content"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập phản hồi!",
              },
            ]}
          >
            <Input.TextArea />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default CartHistory;
