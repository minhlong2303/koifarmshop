import { Button, Form, Image, Input, Table } from "antd";
import React, { useMemo } from "react";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import api from "../../config/axios";

function OrderPage() {
  const data = useSelector((store) => store.order);

  const columns = [
    {
      title: "Image",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="" width={100}></Image>;
      },
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Total Price",
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
      toast.success("Create Order successfully");
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
              <b>Total: </b> {totalAmount} {/* Hiển thị tổng số tiền */}
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
