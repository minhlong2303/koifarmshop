import React, { useEffect, useState } from "react";
import { Button, Image, Layout, Menu, Modal, Table, theme } from "antd";
import api from "../../../config/axios";
const { Header, Content, Footer } = Layout;

const ManageUser = () => {
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name), // Sắp xếp theo tên alphabet (ascending)
    },
    {
      title: "Detail",
      dataIndex: "id",
      key: "id",
      render: (id, student) => {
        return (
          <Button
            type="primary"
            success
            onClick={() => showDetailStudent(student)}
          >
            Detail
          </Button>
        );
      },
    },
  ];
  const [datas, setDatas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const fetchData = async () => {
    try {
      const response = await api.get("users");
      setDatas(response.data);
      console.log(response);
    } catch (error) {
      console.log(error.response.data);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  const showDetailStudent = (student) => {
    console.log(student);

    setSelectedStudent(student);
    setShowModal(true);
  };

  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  return (
    <div>
      <Modal
        title="Student Detail"
        open={showModal}
        onCancel={() => setShowModal(false)}
        onOk={() => setShowModal(false)}
      >
        {/* {selectedStudent && (
              <Descriptions bordered layout="vertical">
                <Descriptions.Item label="ID">
                  {selectedStudent.id}
                </Descriptions.Item>
                <Descriptions.Item label="Name">
                  {selectedStudent.name}
                </Descriptions.Item>
                <Descriptions.Item label="Date Of Birth">
                  {selectedStudent.dateofbirth}
                </Descriptions.Item>
                <Descriptions.Item label="Gender">
                  {selectedStudent.gender ? "Male" : "Female"}
                </Descriptions.Item>
                <Descriptions.Item label="Class">
                  {selectedStudent.class}
                </Descriptions.Item>
                <Descriptions.Item label="Image">
                  <Image
                    src={selectedStudent.image}
                    alt="Student's picture"
                    width={100}
                  ></Image>
                </Descriptions.Item>
                <Descriptions.Item label="Feedback">
                  {selectedStudent.feedback}
                </Descriptions.Item>
              </Descriptions>
            )} */}
      </Modal>
      <Table dataSource={datas} columns={columns}></Table>
    </div>
  );
};
export default ManageUser;
