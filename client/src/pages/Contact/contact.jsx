
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
      <h2>Contact Us</h2>
      <p>
        If you have any questions, feel free to contact us using the form below.
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
              label="Name"
              name="name"
              rules={[{ required: true, message: "Please enter your name!" }]}
            >
              <Input prefix={<UserOutlined />} placeholder="Enter your name" />
            </Form.Item>
            <Form.Item
              label="Email"
              name="email"
              rules={[
                { required: true, message: "Please enter your email!" },
                { type: "email", message: "Please enter a valid email!" },
              ]}
            >
              <Input prefix={<MailOutlined />} placeholder="Enter your email" />
            </Form.Item>
            <Form.Item
              label="Phone"
              name="phone"
              rules={[
                {
                  required: true,
                  message: "Please enter your phone number!",
                },
              ]}
            >
              <Input
                prefix={<PhoneOutlined />}
                placeholder="Enter your phone number"
              />
            </Form.Item>
            <Form.Item
              label="Message"
              name="message"
              rules={[
                { required: true, message: "Please enter your message!" },
              ]}
            >
              <Input.TextArea rows={4} placeholder="Enter your message" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Submit
              </Button>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </div>
  );
};

export default Contact;
