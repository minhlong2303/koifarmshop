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
  };

  return (
    <div className="form-container">
      <h2>Đơn đăng kí ký gửi-bán hộ cá Koi</h2>
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
              rules={[
                {
                  required: true,
                  message: "Vui lòng chọn loại ký gủi",
                },
              ]}
            >
              <Select placeholder="Chọn loại ký gửi">
                <Option value="SALE">Ký gửi để bán</Option>
                <Option value="CARE">Ký gửi để chăm sóc</Option>
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
                <Option value="offline">Ký gửi cá Koi Offline</Option>
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
          name="expectedPrice"
          label="Giá mong muốn"
          rules={[{ required: true, message: "Vui lòng nhập giá mong muốn" }]}
        >
          <Input placeholder="Nhập giá mong muốn" />
        </Form.Item>

        <Form.Item
          name="quantity"
          label="Số lượng"
          rules={[{ required: true, message: "Vui lòng nhập số lượng cá" }]}
        >
          <Input placeholder="Nhập số lượng cá" />
        </Form.Item>

        <Form.Item
          name="careDuration"
          label="Thời gian chăm sóc (ngày)"
          rules={[
            { required: true, message: "Vui lòng nhập thời gian chăm sóc" },
          ]}
        >
          <Input placeholder="Nhập thời gian chăm sóc" />
        </Form.Item>

        <Form.Item
          name="careFee"
          label="Chi phí chăm sóc"
          rules={[
            { required: true, message: "Vui lòng nhập chi phí chăm sóc" },
          ]}
        >
          <Input placeholder="Nhập chi phí chăm sóc" />
        </Form.Item>

        <Form.Item name="specialRequirements" label="Yêu cầu đặc biệt">
          <TextArea rows={3} placeholder="Nhập yêu cầu đặc biệt nếu có" />
        </Form.Item>

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
