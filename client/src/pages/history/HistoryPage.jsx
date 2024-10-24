import React, { useState } from "react";
import { useEffect } from "react";
import api from "../../config/axios";
import "./HistoryPage.scss";
import { Alert, Button, Form, Input, Modal, Rate, Table, Tag } from "antd";
import { useForm } from "antd/es/form/Form";
import { toast } from "react-toastify";

function HistoryPage() {
  const [orders, setOrders] = useState([]);
  const [form] = useForm();
  const [selectedOrder, setSelectedOrder] = useState(null);
  const fetchHistory = async () => {
    try {
      const response = await api.get("/order");
      setOrders(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const columns = [
    {
      title: "Order Date",
      dataIndex: "date",
      key: "date",
      render: (date) => new Date(date).toLocaleDateString(), // Format ngày
    },
    {
      title: "Total Amount",
      dataIndex: "total",
      key: "total",
      render: (total) => `${total.toLocaleString()} VND`, // Định dạng tiền VND
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status) => (
        <Tag color={status === "PAID" ? "green" : "orange"}>{status}</Tag>
      ),
    },
    {
      title: "Order Details",
      dataIndex: "orderDetails",
      key: "orderDetails",
      render: (orderDetails) =>
        orderDetails.map((item) => (
          <div key={item.id} style={{ marginBottom: "10px" }}>
            {/* Thay đổi src và alt cho hình ảnh nếu cần */}
            <strong>Price: {item.price.toLocaleString()} VND</strong> x{" "}
            {item.quantity}
          </div>
        )),
    },
    {
      title: "Payment Method",
      dataIndex: "payment",
      key: "payment",
      render: (payment) => (payment ? payment.payment_method : "Not Paid"),
    },
    {
      title: "Action",
      dataIndex: "id",
      key: "action",
      render: (id) => (
        <Button
          onClick={() => {
            setSelectedOrder(id); // Chọn đơn hàng để gửi feedback
          }}
          type="primary"
        >
          Feedback
        </Button>
      ),
    },
  ];

  useEffect(() => {
    fetchHistory();
  }, []);

  const handleFeedback = async (values) => {
    console.log(values);
    values.orderID = selectedOrder.orderID;
    try {
      await api.post("/feeback/order", values);
      fetchHistory();
      setSelectedOrder(null);
      form.resetFields();
      toast.success("Successfully created feedback");
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <div className="history">
      <h1>Lịch sử đơn hàng</h1>

      <Table dataSource={orders} columns={columns}></Table>

      <Modal
        title="Feedback"
        open={selectedOrder}
        onOk={() => form.submit()}
        onCancel={() => {
          setSelectedOrder(null);
        }}
      >
        <Alert
          message={`Feedback cho đơn hàng #^${selectedOrder?.orderID}`}
          type="info"
        ></Alert>
        <p></p>
        <Form labelCol={{ span: 24 }} onFinish={handleFeedback} form={form}>
          <Form.Item
            label="Rating"
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
            label="Feedback"
            name="feedback"
            rules={[
              {
                required: true,
                message: "Please enter a feedback",
              },
            ]}
          >
            <Input.TextArea></Input.TextArea>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default HistoryPage;
