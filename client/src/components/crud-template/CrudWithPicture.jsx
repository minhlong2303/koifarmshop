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
import { useEffect, useState } from "react";
import { useForm } from "antd/es/form/Form";
import { toast } from "react-toastify";
import { PlusOutlined } from "@ant-design/icons";
import uploadFile from "../../utils/file";
import api from "../../config/axios";

function CRUDPictureTemplate({ columns, formItems, path, idKey }) {
  const [datas, setDatas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [form] = useForm();
  const [loading, setLoading] = useState(false);
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");
  const [fileList, setFileList] = useState([]);
  const tableColumns = [
    ...columns,
    {
      title: "Action",
      dataIndex: idKey,
      key: idKey,
      render: (idKey, koi) => (
        <>
          <Button
            type="primary"
            onClick={() => {
              setShowModal(true);
              form.setFieldsValue(koi);
              if (typeof koi.image === "string" && koi.image) {
                setFileList([
                  {
                    uid: "-1",
                    name: "image.png",
                    status: "done",
                    url: koi.image,
                  },
                ]);
              } else {
                setFileList([]);
              }
            }}
          >
            Update
          </Button>
          <Popconfirm
            title="Do you want to delete ?"
            onConfirm={() => handleDelete(idKey)}
          >
            <Button type="primary" danger>
              Delete
            </Button>
          </Popconfirm>
        </>
      ),
    },
  ];

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
  const handleRemove = (file) => {
    setFileList([]); // Xóa fileList khi ảnh bị xóa
    form.setFieldValue("image", null); // Nếu muốn xóa ảnh trong form khi người dùng xóa
  };
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
      if (values[idKey]) {
        //Update
        if (fileList.length > 0 && fileList[0].originFileObj) {
          const file = fileList[0];
          const url = await uploadFile(file.originFileObj);
          values.image = url;
        }
        const response = await api.put(`${path}/${values[idKey]}`, values);
        toast.success("Update Sucessfully");
      } else {
        //Create
        if (fileList.length > 0) {
          const file = fileList[0];
          console.log(file);
          const url = await uploadFile(file.originFileObj);
          console.log(url);

          values.image = url;
        }
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
    form.resetFields();
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
          <Form.Item label="Image" name="image">
            <Upload
              action="https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"
              listType="picture-card"
              fileList={fileList}
              onPreview={handlePreview}
              onChange={handleChange}
              onRemove={handleRemove}
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

export default CRUDPictureTemplate;
