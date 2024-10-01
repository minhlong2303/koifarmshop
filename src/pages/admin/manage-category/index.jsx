import {
  Button,
  Form,
  Image,
  Input,
  InputNumber,
  Modal,
  Popconfirm,
  Table,
  Upload,
} from "antd";
import { useForm } from "antd/es/form/Form";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { PlusOutlined } from "@ant-design/icons";
import uploadFile from "../../../utils/file";

function ManageCategory() {
  //Quản lí cá Koi
  //1. Xem danh sách cá Koi
  //2. Thêm 1 cá Koi mới
  //3. Update thông tin cho cá Koi
  //4. Delete 1 cá koi

  const [students, setStudents] = useState([]);
  const [openModal, setOpenModal] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [form] = useForm();
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");
  const [fileList, setFileList] = useState([]);

  const api = "https://66dd9794f7bcc0bbdcde7d3c.mockapi.io/KoiFish";

  const fetchKoiFish = async () => {
    // Lấy dữ liệu từ backend
    const response = await axios.get(api);

    console.log(response.data);
    setStudents(response.data);
  };

  useEffect(() => {
    fetchKoiFish();
  }, []);

  const columns = [
    {
      title: "Koi",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="" width={200} />;
      },
    },
    {
      title: "Koi Name",
      dataIndex: "koiName",
      key: "koiName",
    },
    {
      title: "Koi Species",
      dataIndex: "koiSpeciesID",
      key: "koiSpeciesID",
    },
    {
      title: "Koi Age",
      dataIndex: "koiAge",
      key: "koiAge",
    },
    {
      title: "ACTION",
      dataIndex: "koiId",
      key: "koiId",
      render: (koiId) => {
        return (
          <Popconfirm
            title="Delete"
            description="Do you want to delete this Koi Fish?"
            onConfirm={() => handleDeleteKoiFish(koiId)}
          >
            <Button type="primary" danger>
              Delete
            </Button>
          </Popconfirm>
        );
      },
    },
  ];

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const handleSubmitKoiFish = async (koiFish) => {
    if (fileList.length > 0) {
      const file = fileList[0];
      console.log(file);
      const url = await uploadFile(file.originFileObj);
      console.log(url);
      koiFish.image = url;
    }
    console.log(koiFish);
    try {
      setSubmitting(true);
      const response = await axios.post(api, koiFish);
      console.log(response);

      //Lỗi thì bị catch
      //=> thành công thì
      toast.success("Sucessfully create new Koi Fish");
      handleCloseModal();

      form.resetFields();

      fetchKoiFish();
    } catch (err) {
      toast.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  const handleDeleteKoiFish = async (koiFishId) => {
    try {
      await axios.delete(`${api}/${koiFishId}`);
      fetchKoiFish();
      toast.success("Delete sucessfully");
    } catch (error) {
      toast.error("Delete unsucessfully");
      console.log(error);
    }
  };

  const getBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    setPreviewImage(file.url || file.preview);
    setPreviewOpen(true);
  };
  const handleChange = ({ fileList: newFileList }) => setFileList(newFileList);
  const uploadButton = (
    <button
      style={{
        border: 0,
        background: "none",
      }}
      type="button"
    >
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        Upload
      </div>
    </button>
  );

  return (
    <div>
      <h1>KoiFishManagement</h1>
      <Button onClick={handleOpenModal}>Create new student</Button>
      <Table dataSource={students} columns={columns}></Table>
      <Modal
        confirmLoading={submitting}
        onOk={() => form.submit()}
        title="Create new student"
        open={openModal}
        onCancel={handleCloseModal}
      >
        <Form onFinish={handleSubmitKoiFish} form={form}>
          {/*name phải đồng với api */}
          <Form.Item
            label="Koi Fish's name"
            name="koiName"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fishh's name",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's speciesID"
            name="koiSpeciesID"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's speciesID",
              },
              {
                type: "number",
                min: 0,
                max: 20,
                message: "Invalid code",
              },
            ]}
          >
            <InputNumber></InputNumber>
          </Form.Item>
          <Form.Item
            label="Koi Fish's origin"
            name="koiOrigin "
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's origin",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's gender"
            name="koiGender"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's gender",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's Age"
            name="koiAge"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's age",
              },
              {
                type: "number",
                min: 1,
                max: 20,
                message: "Invalid age ",
              },
            ]}
          >
            <InputNumber></InputNumber>
          </Form.Item>
          <Form.Item
            label="Koi Fish's size"
            name="koiSize"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's size",
              },
              {
                type: "number",
                min: 1,
                max: 120,
                message: "Invalid size",
              },
            ]}
          >
            <InputNumber></InputNumber>
          </Form.Item>
          <Form.Item
            label="Koi Fish's breed"
            name="koiBreed"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's breed",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's location"
            name="location"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's Location",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's owner"
            name="owner"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's owner",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's description"
            name="description"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's description",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item
            label="Koi Fish's Id"
            name="koiId"
            rules={[
              {
                required: true,
                message: "Please enter Koi Fish's Id",
              },
            ]}
          >
            <Input></Input>
          </Form.Item>
          <Form.Item label="image" name="image">
            <Upload
              action="https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"
              listType="picture-card"
              fileList={fileList}
              onPreview={handlePreview}
              onChange={handleChange}
            >
              {fileList.length >= 8 ? null : uploadButton}
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
      {previewImage && (
        <Image
          wrapperStyle={{
            display: "none",
          }}
          preview={{
            visible: previewOpen,
            onVisibleChange: (visible) => setPreviewOpen(visible),
            afterOpenChange: (visible) => !visible && setPreviewImage(""),
          }}
          src={previewImage}
        />
      )}
    </div>
  );
}

export default ManageCategory;
