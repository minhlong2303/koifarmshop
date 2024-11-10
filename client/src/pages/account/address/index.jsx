import { useState } from "react";
import { Form, Input, Button, Select, Row, Col, message } from "antd";

const { Option } = Select;

function Address() {
  const [form] = Form.useForm();

  const onFinish = (values) => {
    console.log("Address form values:", values);
    message.success("Địa chỉ đã được cập nhật thành công!");
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
        maxHeight: "60vh",
        overflowY: "auto",
      }}
    >
      <h2 style={{ fontSize: "24px", fontWeight: "bold", marginBottom: "20px" }}>
        Địa chỉ của tôi
      </h2>

      <Form form={form} layout="vertical" onFinish={onFinish}>
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              label="Tên người nhận"
              name="recipientName"
              rules={[{ required: true, message: "Vui lòng nhập tên người nhận!" }]}
            >
              <Input placeholder="Tên người nhận" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label="Số điện thoại"
              name="phone"
              rules={[{ required: true, message: "Vui lòng nhập số điện thoại!" }]}
            >
              <Input addonBefore="+84" placeholder="Số điện thoại" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              label="Thành Phố"
              name="city"
              rules={[{ required: true, message: "Vui lòng chọn thành phố!" }]}
            >
              <Select placeholder="Chọn thành phố">
                {/* Add more options here */}
                <Option value="hanoi">Hà Nội</Option>
                <Option value="hcm">Hồ Chí Minh</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label="Quận / Huyện"
              name="district"
              rules={[{ required: true, message: "Vui lòng chọn quận/huyện!" }]}
            >
              <Select placeholder="Chọn quận/huyện">
                {/* Add more options here */}
                <Option value="baDinh">Ba Đình</Option>
                <Option value="cauGiay">Cầu Giấy</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              label="Phường / Xã"
              name="ward"
              rules={[{ required: true, message: "Vui lòng chọn phường/xã!" }]}
            >
              <Select placeholder="Chọn phường/xã">
                {/* Add more options here */}
                <Option value="phuong1">Phường 1</Option>
                <Option value="phuong2">Phường 2</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label="Địa chỉ chi tiết"
              name="detailedAddress"
              rules={[{ required: true, message: "Vui lòng nhập địa chỉ chi tiết!" }]}
            >
              <Input placeholder="Số nhà, tên đường..." />
            </Form.Item>
          </Col>
        </Row>

        <Form.Item style={{ textAlign: "right" }}>
          <Button type="primary" htmlType="submit">
            Cập nhật địa chỉ
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default Address;