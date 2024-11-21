import { Button, Image, Table } from "antd";
import React, { useMemo } from "react";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import api from "../../config/axios";

function OrderPage() {
  const data = useSelector((store) => store.order);

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
      render: (item) => <span>{item.price * item.quantity}</span>,
    },
  ];

  const handleBuy = async () => {
    try {
      // Chuẩn bị dữ liệu cho API
      const detail = data.map((item) => ({
        itemId: item.koiID || item.batchKoiID,
        quantity: item.quantity,
        itemType: item.koiID ? "koi" : "batch",
        batch: !!item.batchKoiID,
      }));

      const response = await api.post("order", { detail });
      toast.success("Order created successfully!");
      window.open(response.data);

      // Thêm logic xóa giỏ hàng nếu cần
    } catch (error) {
      console.error(error.response?.data || error.message);
      toast.error("Failed to create order");
    }
  };

  const totalAmount = useMemo(
    () => data.reduce((total, item) => total + item.price * item.quantity, 0),
    [data]
  );

  return (
    <div style={{ padding: "60px" }}>
      <Table
        rowKey={(item) => item.koiID || item.batchID}
        columns={columns}
        dataSource={data}
        pagination={false}
        footer={() => (
          <div style={{ textAlign: "right", fontSize: "18px" }}>
            <b>Tổng: </b> {totalAmount}
          </div>
        )}
      />
      <Button onClick={handleBuy}>Tiếp tục</Button>
    </div>
  );
}

export default OrderPage;
