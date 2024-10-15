import React, { useEffect, useState } from "react";
import { Button, Descriptions, Image, Modal, Table, theme } from "antd";
import api from "../../../config/axios";

const ManageUser = () => {
  const columns = [
    {
      title: "ID",
      dataIndex: "id", 
      key: "id",
    },
    {
      title: "Name",
      dataIndex: ["firstName", "lastName"], 
      key: "name",
      render: (text, record) => `${record.firstName} ${record.lastName}`,
      sorter: (a, b) => a.firstName.localeCompare(b.firstName),
    },
    {
      title: "Detail",
      dataIndex: "id",
      key: "id",
      render: (id, user) => (
        <Button type="primary" onClick={() => showDetailUser(user)}>
          Detail
        </Button>
      ),
    },
  ];

  const [datas, setDatas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  const fetchData = async () => {
    try {
      const response = await api.get("user"); //API get User
      setDatas(response.data);
    } catch (error) {
      console.log(error.response.data);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const showDetailUser = (user) => {
    setSelectedUser(user);
    setShowModal(true);
  };

  return (
    <div>
      <Modal
        title="User Detail"
        open={showModal}
        onCancel={() => setShowModal(false)}
        onOk={() => setShowModal(false)}
      >
        {selectedUser && (
          <Descriptions bordered layout="vertical">
            <Descriptions.Item label="ID">
              {selectedUser.id}
            </Descriptions.Item>
            <Descriptions.Item label="Name">
              {selectedUser.firstName} {selectedUser.lastName}
            </Descriptions.Item>
            <Descriptions.Item label="Phone Number">
              {selectedUser.phone}
            </Descriptions.Item>
            <Descriptions.Item label="Email">
              {selectedUser.email}
            </Descriptions.Item>
            <Descriptions.Item label="Image">
              <Image
                src={selectedUser.profileIMG}
                alt="User's picture"
                width={100}
              ></Image>
            </Descriptions.Item>
          </Descriptions>
        )}
      </Modal>
      <Table dataSource={datas} columns={columns}></Table>
    </div>
  );
};

export default ManageUser;
