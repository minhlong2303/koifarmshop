import { Button, Result } from "antd";
import React, { useEffect } from "react";
import useGetParams from "../../hooks/useGetParams";
import api from "../../config/axios";
import { useNavigate } from "react-router-dom";

function SuccessPage() {
  const navigate = useNavigate();
  const params = useGetParams();
  const orderID = params("orderID");
  const vnp_TransactionStatus = params("vnp_TransactionStatus");
  const postOrderID = async () => {
    try {
      const response = await api.post(`/order/pay?orderID=${orderID}`);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    if (vnp_TransactionStatus === "00") {
      postOrderID();
    } else {
      //Chuyen qua trang thanh toan that bai
      navigate("/error");
    }
  }, []);
  return (
    <div>
      <Result
        status="success"
        title="Thanh toán thành công!"
        subTitle="Order number: 2017182818828182881 Cloud server configuration takes 1-5 minutes, please wait."
        extra={[
          <Button type="primary" key="console">
            Xem lịch sử đơn hàng
          </Button>,
          <Button
            onClick={() => {
              navigate("/");
            }}
            key="buy"
          >
            Về trang chủ
          </Button>,
        ]}
      />
    </div>
  );
}

export default SuccessPage;
