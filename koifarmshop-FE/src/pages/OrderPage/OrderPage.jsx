import { Button, Form, Image, Input, Table } from "antd";
import React, { useMemo } from "react";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import api from "../../config/axios";
import { clearOrder } from "../../redux/features/orderSlice";
import { removeSelected } from "../../redux/features/cartSlice";

function OrderPage() {
  const data = useSelector((store) => store.order);
  const columns = [
    {
      title: "Ảnh",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="" width={100}></Image>;
      },
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
      render: (koiFish) => {
        return <span>{koiFish.price * koiFish.quantity}</span>;
      },
    },
  ];
  const handleBuy = async () => {
    try {
      const detail = data.map((koi) => ({
        koiId: koi.koiID,
        quantity: koi.quantity,
      }));
      const response = await api.post("order", { detail });
      window.open(response.data);
    } catch (error) {
      console.log(error.response.data);
      toast.error("Failed to create order");
    }
  };
  const totalAmount = useMemo(() => {
    return data.reduce((total, item) => total + item.price * item.quantity, 0);
  }, [data]);
  return (
    <>
      <div
        style={{
          padding: "60px",
        }}
      >
        <Table
          rowKey="koiID"
          columns={columns}
          dataSource={data}
          pagination={false} // Không phân trang nếu bạn muốn hiển thị tất cả sản phẩm cùng một lúc
          footer={() => (
            <div style={{ textAlign: "right", fontSize: "18px" }}>
              <b>Tổng: </b> {totalAmount} {/* Hiển thị tổng số tiền */}
            </div>
          )}
        />
        {/* <Form labelCol={{ span: 24 }}>
          <Form.Item
            label="Họ và tên"
            name="name"
            rules={[
              {
                required: true,
                message: "Please enter Species's name",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Số Điện Thoại"
            name="phone"
            rules={[
              {
                required: true,
                message: "Please enter Species's name",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Địa chỉ"
            name="address"
            rules={[
              {
                required: true,
                message: "Please enter address",
              },
            ]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            label="Ghi chú"
            name="note"
            rules={[
              {
                required: true,
                message: "Please enter address",
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Form> */}

        <Button onClick={handleBuy}>Tiếp tục</Button>
      </div>
    </>
  );
}

export default OrderPage;
