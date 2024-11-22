import React, { useState, useMemo } from "react";
import {
  Button,
  Image,
  Input,
  Card,
  List,
  Typography,
  Row,
  Col,
  message,
} from "antd";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import api from "../../config/axios";

const OrderPage = () => {
  const data = useSelector((store) => store.order);

  const [customerInfo, setCustomerInfo] = useState({
    fullName: "",
    phone: "",
    email: "",
    address: "",
  });

  const columns = [
    {
      title: "Ảnh",
      dataIndex: "image",
      key: "image",
      render: (image) => <Image src={image} alt="" width={100} />,
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Tổng tiền",
      key: "totalPrice",
      render: (item) => <span>{item.price * item.quantity} VND</span>,
    },
  ];

  const handleBuy = async () => {
    if (
      !customerInfo.fullName ||
      !customerInfo.phone ||
      !customerInfo.email ||
      !customerInfo.address
    ) {
      message.error("Vui lòng điền đầy đủ thông tin.");
      return;
    }

    // Kiểm tra số lượng của sản phẩm, nếu không hợp lệ thì thông báo lỗi
    const invalidItems = data.filter((item) => item.quantity <= 0);
    if (invalidItems.length > 0) {
      message.error("Số lượng sản phẩm phải lớn hơn 0.");
      return;
    }

    try {
      // Chuẩn bị dữ liệu cho API, bao gồm itemId, quantity, itemType, và batch
      const detail = data.map((item) => ({
        itemId: item.koiID || item.batchKoiID, // Kiểm tra itemId từ koiID hoặc batchKoiID
        quantity: item.quantity,
        itemType: item.koiID ? "koi" : "batch", // Phân biệt loại sản phẩm
        batch: !!item.batchKoiID, // Kiểm tra nếu sản phẩm là lô cá
      }));

      // Gửi yêu cầu POST đến API để tạo đơn hàng
      const response = await api.post("order", { detail });
      toast.success("Đơn hàng đã được tạo thành công!");

      // Mở trang thanh toán sau khi đơn hàng được tạo thành công
      window.open(response.data);

      // Thêm logic xóa giỏ hàng nếu cần
    } catch (error) {
      console.error(error.response?.data || error.message);
      toast.error("Không thể tạo đơn hàng");
    }
  };

  const totalAmount = useMemo(
    () => data.reduce((total, item) => total + item.price * item.quantity, 0),
    [data]
  );

  return (
    <div style={{ padding: "30px", backgroundColor: "#f9f9f9" }}>
      <Row gutter={32}>
        {/* Phần thông tin người mua */}
        <Col span={12}>
          <Card title="Thông tin của bạn" style={{ width: "100%" }}>
            <Input
              placeholder="Họ và tên"
              value={customerInfo.fullName}
              onChange={(e) =>
                setCustomerInfo({ ...customerInfo, fullName: e.target.value })
              }
              style={{ marginBottom: "10px" }}
            />
            <Input
              placeholder="Số điện thoại"
              value={customerInfo.phone}
              onChange={(e) =>
                setCustomerInfo({ ...customerInfo, phone: e.target.value })
              }
              style={{ marginBottom: "10px" }}
            />
            <Input
              placeholder="Email"
              value={customerInfo.email}
              onChange={(e) =>
                setCustomerInfo({ ...customerInfo, email: e.target.value })
              }
              style={{ marginBottom: "10px" }}
            />
            <Input
              placeholder="Địa chỉ"
              value={customerInfo.address}
              onChange={(e) =>
                setCustomerInfo({ ...customerInfo, address: e.target.value })
              }
              style={{ marginBottom: "10px" }}
            />
          </Card>
        </Col>

        {/* Phần danh sách sản phẩm và tổng tiền */}
        <Col span={12}>
          <Card title="Đơn hàng của bạn" style={{ width: "100%" }}>
            <List
              itemLayout="horizontal"
              dataSource={data}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    title={item.name}
                    description={`Giá: ${item.price} VND x ${item.quantity}`}
                  />
                </List.Item>
              )}
            />
            <Row justify="space-between" style={{ marginTop: "20px" }}>
              <Typography.Text>Tổng tiền: </Typography.Text>
              <Typography.Text strong>{totalAmount} VND</Typography.Text>
            </Row>
            <Button
              type="primary"
              style={{ marginTop: "20px", width: "100%" }}
              onClick={handleBuy}
            >
              Tiếp tục
            </Button>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default OrderPage;
