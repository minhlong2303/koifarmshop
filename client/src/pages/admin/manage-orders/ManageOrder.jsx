import { useState } from "react";
import { Button, Descriptions, Image, Select, message } from "antd";
import ManageTemplate from "../../../components/manage-template";
import api from "../../../config/axios";

const { Option } = Select;

const ManageOrder = () => {
  const [loading, setLoading] = useState(false);

  const handleStatusChange = async (orderId, newStatus) => {
    setLoading(true);
    try {
      const response = await api.put(`/order/${orderId}/status`, null, {
        params: { status: newStatus },
      });
      message.success(`Order status updated to ${newStatus}`);
    } catch (error) {
      message.error("Failed to update order status");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const columns = [
    {
      title: "OrderID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Total",
      dataIndex: "total",
      key: "total",
      render: (total) =>
        total !== undefined ? `${total.toLocaleString()} VND` : "N/A",
      sorter: (a, b) => a.total - b.total,
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status, record) => (
        <Select
          value={status}
          onChange={(value) => handleStatusChange(record.id, value)}
          loading={loading}
          disabled={loading}
        >
          <Option value="PENDING">Pending</Option>
          <Option value="PROCESSING">Processing</Option>
          <Option value="COMPLETED">Completed</Option>
          <Option value="CANCELLED">Cancelled</Option>
        </Select>
      ),
    },
  ];

  const userDetail = ({ record }) => (
    <Descriptions bordered layout="vertical">
      <Descriptions.Item label="ID">{record.id}</Descriptions.Item>
      <Descriptions.Item label="Total">{record.total} VND</Descriptions.Item>
      <Descriptions.Item label="Status">{record.status}</Descriptions.Item>
      <Descriptions.Item label="Date">
        {new Date(record.date).toLocaleString()}
      </Descriptions.Item>
      <Descriptions.Item label="Order Details">
        {record.orderDetails.map((detail) => (
          <div key={detail.id}>
            - Price: {detail.price.toLocaleString()} VND, Quantity:{" "}
            {detail.quantity}
          </div>
        ))}
      </Descriptions.Item>
      <Descriptions.Item label="Payment Status">
        {record.payment ? record.payment.status : "No Payment"}
      </Descriptions.Item>
    </Descriptions>
  );

  return (
    <div>
      <ManageTemplate
        columns={columns}
        DescriptionsForm={userDetail}
        path="order"
        idKey="id"
      />
    </div>
  );
};

export default ManageOrder;
