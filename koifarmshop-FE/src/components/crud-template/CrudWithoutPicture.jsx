import {
  Button,
  Form,
  Image,
  Input,
  Modal,
  Popconfirm,
  Table,
  Upload,
} from "antd";
import React, { useEffect, useState } from "react";
import { useForm } from "antd/es/form/Form";
import { toast } from "react-toastify";
import { PlusOutlined } from "@ant-design/icons";
import uploadFile from "../../utils/file";
import api from "../../config/axios";

function CRUDTemplate({ columns, formItems, path }) {
  const [datas, setDatas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [form] = useForm();
  const [loading, setLoading] = useState(false);
  const tableColumns = [
    ...columns,
    {
      title: "Action",
      dataIndex: "SpeciesID",
      key: "SpeciesID",
      render: (koiId, koiFish) => (
        <>
          <Button
            type="primary"
            onClick={() => {
              setShowModal(true);
              form.setFieldsValue(koiFish);
            }}
          >
            Edit
          </Button>
          <Popconfirm
            title="Do you want to delete this category"
            onConfirm={() => handleDelete(koiId)}
          >
            <Button type="primary" danger>
              Delete
            </Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  //Get
  const fetchData = async () => {
    try {
      const response = await api.get(path);
      setDatas(response.data);
    } catch (error) {
      console.log(error.response.data);
    }
  };
  //Create or update
  const handleSubmit = async (values) => {
    console.log(values);
    try {
      setLoading(true);
      if (values.SpeciesID) {
        //Update
        const response = await api.put(`${path}/${values.SpeciesID}`, values);
        toast.success("Update Sucessfully");
      } else {
        //Create
        const response = await api.post(path, values);
        toast.success("Add Sucessfully");
      }
      fetchData();
      form.resetFields();
      setShowModal(false);
    } catch (error) {
      toast.error(error.response.data);
    } finally {
      setLoading(false);
    }
  };

  //Delete
  const handleDelete = async (id) => {
    try {
      const response = await api.delete(`${path}/${id}`);
      toast.success("Delete Sucessfully");
      fetchData();
    } catch (error) {
      toast.error(error.response.data);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleOpenModal = () => {
    setShowModal(true);
  };

  return (
    <div>
      <Button onClick={handleOpenModal}>Add {path}</Button>
      <Table dataSource={datas} columns={tableColumns}></Table>
      <Modal
        open={showModal}
        onCancel={handleCloseModal}
        title={path}
        onOk={() => form.submit()}
        confirmLoading={loading}
      >
        <Form form={form} labelCol={{ span: 24 }} onFinish={handleSubmit}>
          {formItems}
        </Form>
      </Modal>
    </div>
  );
}

export default CRUDTemplate;
