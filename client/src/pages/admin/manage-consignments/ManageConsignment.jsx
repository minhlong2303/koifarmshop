import React, { useState, useEffect } from "react";
import ManageTemplate from "../../../components/manage-template";
import { Descriptions, message, Select, Table, Image } from "antd";
import api from "../../../config/axios";
const { Option } = Select;

function ManageConsignment() {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);

  //   // Fetch dữ liệu consignment
  //   useEffect(() => {
  //     const fetchConsignment = async () => {
  //       try {
  //         const response = await api.get("consignments");
  //         setData(response.data);
  //       } catch (error) {
  //         message.error("Failed to fetch consignments.");
  //         console.error(error);
  //       }
  //     };
  //     fetchConsignment();
  //   }, []);

  // Cập nhật trạng thái consignment
  const handleStatusChange = async (consignmentId, newStatus) => {
    setLoading(true);
    try {
      const response = await api.put(
        `/consignment/${consignmentId}/status`,
        null,
        {
          params: { status: newStatus },
        }
      );
      message.success(`Consignment status updated to ${newStatus}`);
      setData((prevData) =>
        prevData.map((item) =>
          item.id === consignmentId ? { ...item, status: newStatus } : item
        )
      );
    } catch (error) {
      message.error("Failed to update consignment status.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  // Định nghĩa cột của bảng
  const columns = [
    {
      title: "Consignment ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Koi Name",
      dataIndex: "koiName",
      key: "koiName",
    },
    {
      title: "Expected Price",
      dataIndex: "expectedPrice",
      key: "expectedPrice",
      render: (price) => (price ? `${price.toLocaleString()} VND` : "N/A"),
      sorter: (a, b) => a.expectedPrice - b.expectedPrice,
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

  // Hiển thị chi tiết consignment
  const userDetail = ({ record }) => (
    <Descriptions bordered layout="vertical">
      <Descriptions.Item label="Consignment ID">{record.id}</Descriptions.Item>
      <Descriptions.Item label="Koi Name">{record.koiName}</Descriptions.Item>
      <Descriptions.Item label="Breed">{record.breed}</Descriptions.Item>
      <Descriptions.Item label="Size">{record.size} cm</Descriptions.Item>
      <Descriptions.Item label="Age">{record.age} years</Descriptions.Item>
      <Descriptions.Item label="Gender">{record.gender}</Descriptions.Item>
      <Descriptions.Item label="Expected Price">
        {record.expectedPrice.toLocaleString()} VND
      </Descriptions.Item>
      <Descriptions.Item label="Consignment Type">
        {record.consignmentType}
      </Descriptions.Item>
      <Descriptions.Item label="Inspection Method">
        {record.inspectionMethod}
      </Descriptions.Item>
      <Descriptions.Item label="Status">{record.status}</Descriptions.Item>
      <Descriptions.Item label="Created Date">
        {new Date(record.createdDate).toLocaleString()}
      </Descriptions.Item>
      <Descriptions.Item label="Koi Image">
        <Image src={record.koiImageUrl} alt="Koi" width={200} />
      </Descriptions.Item>
      <Descriptions.Item label="Certificate Image">
        <Image src={record.certificateImageUrl} alt="Certificate" width={200} />
      </Descriptions.Item>
    </Descriptions>
  );

  return (
    <div>
      <ManageTemplate
        columns={columns}
        DescriptionsForm={userDetail}
        dataSource={data}
        path="consignments"
        idKey="id"
      />
    </div>
  );
}

export default ManageConsignment;
