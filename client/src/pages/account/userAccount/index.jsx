import { useState } from "react";
import { Form, Input, Button, Select, Upload, message, Row, Col } from "antd";
import { UploadOutlined } from "@ant-design/icons";

const { Option } = Select;

const UserAccount = () => {
  const [form] = Form.useForm();
  const [image, setImage] = useState(null);

  const handleImageChange = (info) => {
    if (info.file.status === "done") {
      setImage(info.file.originFileObj);
      message.success(`${info.file.name} file uploaded successfully`);
    } else if (info.file.status === "error") {
      message.error(`${info.file.name} file upload failed.`);
    }
  };

  const onFinish = (values) => {
    console.log("Form values:", values);
  };

  return (
    <div
      style={{
        padding: "20px",
        backgroundColor: "#fff",
        borderRadius: "8px",
        boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
        maxWidth: "900px",
        margin: "0 auto",
      }}
    >
      <h2
        style={{ fontSize: "24px", fontWeight: "bold", marginBottom: "20px" }}
      >
        Hồ sơ cá nhân
      </h2>

      <Row gutter={16}>
        <Col span={6} style={{ textAlign: "center" }}>
          <Upload
            name="avatar"
            listType="picture-card"
            showUploadList={false}
            beforeUpload={(file) => {
              const isValid =
                file.size < 2 * 1024 * 1024 &&
                file.width >= 400 &&
                file.height >= 400;
              if (!isValid) {
                message.error(
                  "Image must be at least 400x400px and smaller than 2MB!"
                );
              }
              return isValid;
            }}
            onChange={handleImageChange}
          >
            {image ? (
              <img
                src={URL.createObjectURL(image)}
                alt="avatar"
                style={{ width: "100%" }}
              />
            ) : (
              <UploadOutlined style={{ fontSize: "40px" }} />
            )}
          </Upload>
          <p>Image requirements:</p>
          <ul style={{ paddingLeft: "20px", textAlign: "left" }}>
            <li>Min. 400 x 400px</li>
            <li>Max. 2MB</li>
          </ul>
        </Col>

        <Col span={18}>
          <Form
            form={form}
            layout="vertical"
            onFinish={onFinish}
            initialValues={{
              email: "doquangdung1782004@gmail.com",
              username: "QuangDung2k4",
            }}
          >
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Email" name="email">
                  <Input disabled />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  label="Số điện thoại"
                  name="phone"
                  rules={[
                    {
                      required: true,
                      message: "Please input your phone number!",
                    },
                  ]}
                >
                  <Input addonBefore="+84" placeholder="Số điện thoại" />
                </Form.Item>
              </Col>
            </Row>

            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Tên tài khoản" name="username">
                  <Input />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Giới tính" name="gender">
                  <Select placeholder="Chọn giới tính">
                    <Option value="male">Nam</Option>
                    <Option value="female">Nữ</Option>
                    <Option value="other">Khác</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Họ" name="lastname">
                  <Input placeholder="Họ" />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Tên" name="firstname">
                  <Input placeholder="Tên" />
                </Form.Item>
              </Col>
            </Row>

            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Tỉnh" name="city">
                  <Select placeholder="Thành phố">
                    {/* Add more options here */}
                    <Option value="hanoi">Hà Nội</Option>
                    <Option value="hcm">Hồ Chí Minh</Option>
                  </Select>
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Quận / Huyện" name="district">
                  <Select placeholder="Quận / Huyện">
                    {/* Add more options here */}
                    <Option value="baDinh">Ba Đình</Option>
                    <Option value="cauGiay">Cầu Giấy</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Phường / Xã" name="ward">
                  <Select placeholder="Phường / Xã">
                    {/* Add more options here */}
                    <Option value="phuong1">Phường 1</Option>
                    <Option value="phuong2">Phường 2</Option>
                  </Select>
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Địa chỉ" name="address">
                  <Input placeholder="Địa chỉ" />
                </Form.Item>
              </Col>
            </Row>

            <Form.Item style={{ textAlign: "right" }}>
              <Button type="primary" htmlType="submit">
                Cập nhật
              </Button>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default UserAccount;
