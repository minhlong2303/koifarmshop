import { useState } from "react";
import { Form, Input, Button, Select, Upload, Row, Col, Image } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./index.css";
import api from "../../../config/axios";
import { useForm } from "antd/es/form/Form";
import uploadFile from "../../../utils/file";
import { useNavigate } from "react-router-dom";

const { Option } = Select;
const { TextArea } = Input;

function KoiConsignment() {
  const navigate = useNavigate();
  const [form] = useForm();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");
  const [koiImageList, setKoiImageList] = useState([]); // Dùng riêng cho ảnh cá Koi
  const [certificateImageList, setCertificateImageList] = useState([]);
  const handleSubmit = async (values) => {
    try {
      const formData = new FormData();
      setIsSubmitting(true);
      if (koiImageList.length > 0) {
        const fileKoiImage = koiImageList[0];
        const koiUrl = await uploadFile(fileKoiImage.originFileObj);
        values.koiImageUrl = koiUrl;
      }
      if (certificateImageList.length > 0) {
        const fileCertificateList = certificateImageList[0];
        const certificateUrl = await uploadFile(
          fileCertificateList.originFileObj
        );
        values.certificateImageUrl = certificateUrl;
      }
      // // Append form fields to formData
      // Object.keys(values).forEach((key) => {
      //   formData.append(key, values[key]);
      // });

      // // Append file fields to formData
      // if (values.koiImage?.file) {
      //   formData.append("koiImage", values.koiImage.file.originFileObj);
      // }
      // if (values.certificateImage?.file) {
      //   formData.append(
      //     "certificateImage",
      //     values.certificateImage.file.originFileObj
      //   );
      // }

      await api.post("consignments", values);
      toast.success("Đơn ký gửi đã được gửi thành công");
      navigate("/consignment-success");
    } catch (error) {
      console.log(error);
      toast.error("Có lỗi xảy ra khi gửi đơn ký gửi");
    } finally {
      setIsSubmitting(false);
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
  const handleKoiImageChange = ({ fileList: newFileList }) =>
    setKoiImageList(newFileList);
  const handleCertificateImageChange = ({ fileList: newFileList }) =>
    setCertificateImageList(newFileList);
  const uploadButton = (
    <button
      style={{
        border: 0,
        background: "none",
      }}
      type="button"
    >
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
    <div className="form-container">
      <h2>Đơn đăng kí ký gửi - Bán hộ cá Koi</h2>
      <Form form={form} layout="vertical" onFinish={handleSubmit}>
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="firstName"
              label="Tên khách hàng"
              rules={[
                { required: true, message: "Vui lòng nhập tên khách hàng" },
              ]}
            >
              <Input placeholder="Nhập tên khách hàng" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="lastName"
              label="Họ khách hàng"
              rules={[
                { required: true, message: "Vui lòng nhập họ khách hàng" },
              ]}
            >
              <Input placeholder="Nhập họ khách hàng" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="phone"
              label="Số điện thoại"
              rules={[
                { required: true, message: "Vui lòng nhập số điện thoại" },
              ]}
            >
              <Input placeholder="Nhập số điện thoại" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="email"
              label="Email"
              rules={[{ required: true, message: "Vui lòng nhập email" }]}
            >
              <Input type="email" placeholder="Nhập email" />
            </Form.Item>
          </Col>
        </Row>

        <Form.Item
          name="address"
          label="Địa chỉ"
          rules={[{ required: true, message: "Vui lòng nhập địa chỉ" }]}
        >
          <Input placeholder="Nhập địa chỉ" />
        </Form.Item>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="consignmentType"
              label="Loại ký gửi"
              rules={[{ required: true, message: "Vui lòng chọn loại ký gửi" }]}
            >
              <Select placeholder="Chọn loại ký gửi">
                <Option value="SALE">Ký gửi Cá Koi để bán</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="inspectionMethod"
              label="Hình thức ký gửi"
              rules={[
                { required: true, message: "Vui lòng chọn hình thức ký gửi" },
              ]}
            >
              <Select placeholder="Chọn hình thức ký gửi">
                <Option value="online">Ký gửi cá Koi Online</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="koiName"
              label="Tên cá Koi"
              rules={[{ required: true, message: "Vui lòng nhập tên cá Koi" }]}
            >
              <Input placeholder="Nhập tên cá Koi" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="breed"
              label="Chủng loại"
              rules={[{ required: true, message: "Vui lòng nhập chủng loại" }]}
            >
              <Input placeholder="ví dụ: Showa, Kohaku,..." />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="size"
              label="Kích thước"
              rules={[{ required: true, message: "Vui lòng nhập kích thước" }]}
            >
              <Input placeholder="ví dụ: 100cm, 90cm,..." />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              name="age"
              label="Tuổi"
              rules={[{ required: true, message: "Vui lòng nhập tuổi" }]}
            >
              <Input placeholder="Nhập tuổi" />
            </Form.Item>
          </Col>
        </Row>

        <Form.Item
          name="gender"
          label="Giới tính"
          rules={[{ required: true, message: "Vui lòng chọn giới tính" }]}
        >
          <Select placeholder="Chọn giới tính">
            <Option value="male">Đực</Option>
            <Option value="female">Cái</Option>
          </Select>
        </Form.Item>

        <Form.Item
          name="koiImageUrl"
          label="Ảnh cá Koi"
          valuePropName="file"
          getValueFromEvent={(e) => (Array.isArray(e) ? e[0] : e)}
          rules={[{ required: true, message: "Vui lòng tải lên ảnh cá Koi" }]}
        >
          <Upload
            action="https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"
            listType="picture-card"
            fileList={koiImageList}
            onPreview={handlePreview}
            onChange={handleKoiImageChange}
          >
            {koiImageList.length >= 8 ? null : uploadButton}
          </Upload>
        </Form.Item>

        <Form.Item
          name="certificateImageUrl"
          label="Chứng nhận của cá Koi (Nếu có)"
          valuePropName="file"
          getValueFromEvent={(e) => (Array.isArray(e) ? e[0] : e)}
        >
          <Upload
            action="https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"
            listType="picture-card"
            fileList={certificateImageList}
            onPreview={handlePreview}
            onChange={handleCertificateImageChange}
          >
            {certificateImageList.length >= 8 ? null : uploadButton}
          </Upload>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={isSubmitting}>
            Gửi đơn ký gửi
          </Button>
        </Form.Item>
      </Form>
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

export default KoiConsignment;
