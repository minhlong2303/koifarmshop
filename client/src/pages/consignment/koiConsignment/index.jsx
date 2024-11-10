import { useState } from "react";
import { Form, Input, Button, Select, Upload, message, Row, Col } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./index.css";
import api from "../../../config/axios";
import { useForm } from "antd/es/form/Form";

const { Option } = Select;
const { TextArea } = Input;

function KoiConsignment() {
  const [form] = useForm();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (values) => {
    try {
      setIsSubmitting(true);
      const response = await api.post("consignments", values);
      toast.success("Đơn ký gửi đã được gửi thành công");
    } catch (error) {
      console.log(error);
    } finally {
      setIsSubmitting(false);
    }
    // Giả lập gọi API sau khi submit
    // setTimeout(() => {
    //   setIsSubmitting(false);
    //   toast.success("Đơn ký gửi đã được gửi thành công!");
    //   form.resetFields(); // Reset lại form sau khi submit thành công
    // }, 1500);
  };

  const handleUploadChange = (info) => {
    if (info.file.status === "done") {
      message.success(`${info.file.name} tải lên thành công`);
    } else if (info.file.status === "error") {
      message.error(`${info.file.name} tải lên thất bại.`);
    }
  };

  return (
    <div className="form-container">
      <h2>Đơn đăng kí ký gửi-bán hộ cá Koi</h2>
      <Form
        form={form}
        layout="vertical"
        onFinish={handleSubmit}
        initialValues={{
          consignmentType: "",
        }}
      >
        <Row gutter={16}>
          <Col span={8}>
            <Form.Item
              name="consignmentType"
              label="Hình thức ký gửi"
              rules={[
                { required: true, message: "Vui lòng chọn hình thức ký gửi" },
              ]}
            >
              <Select placeholder="Chọn hình thức ký gửi">
                <Option value="online">Ký gửi cá Koi Online</Option>
                <Option value="offline">Ký gửi cá Koi Offline</Option>
              </Select>
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="koiName"
              label="Tên cá Koi"
              rules={[{ required: true, message: "Vui lòng nhập tên cá Koi" }]}
            >
              <Input placeholder="Nhập tên cá Koi" />
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="age"
              label="Năm sinh"
              rules={[
                {
                  required: true,
                  message: "Vui lòng nhập năm sinh của cá Koi",
                },
              ]}
            >
              <Input placeholder="ví dụ: 2024, 2023,..." />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={8}>
            <Form.Item
              name="gender"
              label="Giới tính"
              rules={[
                { required: true, message: "Vui lòng chọn giới tính cá Koi" },
              ]}
            >
              <Select placeholder="Chọn giới tính cá Koi">
                <Option value="male">Đực</Option>
                <Option value="female">Cái</Option>
              </Select>
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="koiOrigin"
              label="Nguồn gốc"
              rules={[{ required: true, message: "Vui lòng nhập nguồn gốc" }]}
            >
              <Input placeholder="Nhập nguồn gốc" />
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="koiSpecies"
              label="Chủng loại"
              rules={[{ required: true, message: "Vui lòng nhập chủng loại" }]}
            >
              <Input placeholder="ví dụ: Showa, Kohaku,..." />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={8}>
            <Form.Item
              name="koiTemperament"
              label="Tính cách"
              rules={[{ required: true, message: "Vui lòng nhập tính cách" }]}
            >
              <Input placeholder="ví dụ: hiền, hung dữ,..." />
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="feedingAmount"
              label="Lượng thức ăn/ngày"
              rules={[
                { required: true, message: "Vui lòng nhập lượng thức ăn/ngày" },
              ]}
            >
              <Input placeholder="Nhập lượng thức ăn/ngày" />
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item
              name="size"
              label="Kích thước"
              rules={[{ required: true, message: "Vui lòng nhập kích thước" }]}
            >
              <Input placeholder="ví dụ: 100cm, 90cm,..." />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={8}>
            <Form.Item name="illnesses" label="Các bệnh đã từng bị (nếu có)">
              <Input placeholder="ví dụ: chảy máu,..." />
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item name="note" label="Ghi chú">
              <TextArea rows={3} placeholder="Nhập ghi chú" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={8}>
            <Form.Item
              name="images"
              label="Hình ảnh"
              // rules={[
              //   { required: true, message: "Vui lòng tải lên hình ảnh cá Koi" },
              // ]}
            >
              <Upload
                name="file"
                listType="picture"
                onChange={handleUploadChange}
              >
                <Button icon={<UploadOutlined />}>Chọn hình ảnh</Button>
              </Upload>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="video"
              label="Video"
              // rules={[
              //   { required: true, message: "Vui lòng tải lên video cá Koi" },
              // ]}
            >
              <Upload
                name="file"
                listType="picture"
                onChange={handleUploadChange}
              >
                <Button icon={<UploadOutlined />}>Chọn video</Button>
              </Upload>
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item name="certificate" label="Giấy chứng nhận">
              <Upload
                name="file"
                listType="picture"
                onChange={handleUploadChange}
              >
                <Button icon={<UploadOutlined />}>
                  Tải lên giấy chứng nhận
                </Button>
              </Upload>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="customerName"
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
              name="phone"
              label="Số điện thoại"
              rules={[
                { required: true, message: "Vui lòng nhập số điện thoại" },
              ]}
            >
              <Input placeholder="Nhập số điện thoại" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              name="address"
              label="Địa chỉ"
              rules={[{ required: true, message: "Vui lòng nhập địa chỉ" }]}
            >
              <Input placeholder="Nhập địa chỉ" />
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

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={isSubmitting}>
            Gửi đơn ký gửi
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default KoiConsignment;
