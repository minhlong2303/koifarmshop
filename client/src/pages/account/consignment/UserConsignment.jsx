import React, { useEffect, useState } from "react";
import { Table, Tag, Button, message, Spin, Popconfirm } from "antd";
import api from "../../../config/axios"; // Cấu hình axios cho API

const UserConsignment = () => {
  const [consignments, setConsignments] = useState([]);
  const [loading, setLoading] = useState(false);

  // Gọi API để lấy danh sách consignment
  const fetchConsignments = async () => {
    setLoading(true);
    try {
      const response = await api.get("/api/consignment");
      setConsignments(response.data);
    } catch (error) {
      message.error("Không thể tải danh sách consignment.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  // Xóa consignment
  const deleteConsignment = async (id) => {
    try {
      await api.delete(`/api/consignment/${id}`);
      message.success("Xóa consignment thành công!");
      fetchConsignments(); // Cập nhật danh sách sau khi xóa
    } catch (error) {
      message.error("Xóa consignment thất bại.");
      console.error(error);
    }
  };

  // Cột hiển thị cho bảng
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Tên Consignment",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Ngày tạo",
      dataIndex: "createdAt",
      key: "createdAt",
      render: (date) => new Date(date).toLocaleDateString(),
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (status) => {
        const color = status === "Active" ? "green" : "red";
        return <Tag color={color}>{status}</Tag>;
      },
    },
    {
      title: "Tổng giá trị",
      dataIndex: "totalValue",
      key: "totalValue",
      render: (value) => `${value.toLocaleString()} VND`,
    },
    {
      title: "Hành động",
      key: "action",
      render: (_, record) => (
        <div style={{ display: "flex", gap: "10px" }}>
          <Button
            type="link"
            onClick={() => {
              message.info(`Xem chi tiết consignment: ${record.id}`);
            }}
          >
            Chi tiết
          </Button>
          <Button
            type="primary"
            onClick={() => {
              message.info(`Cập nhật consignment: ${record.id}`);
            }}
          >
            Cập nhật
          </Button>
          <Popconfirm
            title="Bạn có chắc muốn xóa consignment này không?"
            onConfirm={() => deleteConsignment(record.id)}
            okText="Có"
            cancelText="Không"
          >
            <Button danger>Xóa</Button>
          </Popconfirm>
        </div>
      ),
    },
  ];

  // Gọi API khi component được mount
  useEffect(() => {
    fetchConsignments();
  }, []);

  return (
    <div
      style={{
        padding: "20px",
        backgroundColor: "#fff",
        borderRadius: "8px",
        boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
        maxWidth: "1200px",
        margin: "0 auto",
      }}
    >
      <h2
        style={{ fontSize: "24px", fontWeight: "bold", marginBottom: "20px" }}
      >
        Quản lý Consignment
      </h2>

      {loading ? (
        <div style={{ textAlign: "center" }}>
          <Spin size="large" />
        </div>
      ) : (
        <Table
          dataSource={consignments}
          columns={columns}
          rowKey={(record) => record.id}
          pagination={{
            pageSize: 5,
            showSizeChanger: true,
            pageSizeOptions: ["5", "10", "20"],
          }}
        />
      )}
    </div>
  );
};

export default UserConsignment;
