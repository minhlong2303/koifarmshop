
import {Form, Input, Button, Row, Col } from "antd";
import { MailOutlined, PhoneOutlined, UserOutlined } from "@ant-design/icons";
import "antd/dist/reset.css";



const Contact = () => {
  const [form] = Form.useForm();

  const handleSubmit = (values) => {
    console.log("Form values: ", values);
  };

  return (
    <div style={{ textAlign: "center" }}>
      <h2>Liên hệ</h2>
      <p>
      Nếu bạn có bất kì câu hỏi nào hãy liên hệ với chúng tôi qua form bên dưới
      </p>
      <Row justify="center">
        <Col xs={24} sm={16} md={12} lg={8}>
          <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            style={{ textAlign: "left" }}
          >
            <Form.Item
              label="Họ và tên"
              name="name"
              rules={[{ required: true, message: "Vui lòng nhập tên của bạn!" }]}
            >
              <Input prefix={<UserOutlined />} placeholder="Nhập tên của bạn" />
            </Form.Item>
            <Form.Item
              label="Email"
              name="email"
              rules={[
                { required: true, message: "Vui lòng nhập Email của bạn!" },
                { type: "email", message: "Please enter a valid email!" },
              ]}
            >
              <Input prefix={<MailOutlined />} placeholder="Nhập địa chỉ Email của bạn" />
            </Form.Item>
            <Form.Item
              label="Số điện thoại"
              name="phone"
              rules={[
                {
                  required: true,
                  message: "Vui lòng nhập số điện thoại của bạn!",
                },
              ]}
            >
              <Input
                prefix={<PhoneOutlined />}
                placeholder="Nhập số điện thoại của bạn"
              />
            </Form.Item>
            <Form.Item
              label="Nội dung"
              name="message"
              rules={[
                { required: true, message: "Vui lòng nhập nội dung!" },
              ]}
            >
              <Input.TextArea rows={4} placeholder="Nhập nội dung của bạn" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Gửi
              </Button>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default Contact;
