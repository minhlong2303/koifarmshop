// SuccessPage.js
import { Button, Result } from "antd";
import React, { useEffect } from "react";
import useGetParams from "../../hooks/useGetParams";
import api from "../../config/axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { clearOrder } from "../../redux/features/orderSlice";
import { removeSelected } from "../../redux/features/cartSlice";

function SuccessPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const params = useGetParams();
  const orderID = params("orderID");
  const vnp_TransactionStatus = params("vnp_TransactionStatus");

  const postOrderID = async () => {
    try {
      const response = await api.post(`/order/pay?orderID=${orderID}`);
      console.log("Response data:", response.data);
      return response.data;
    } catch (error) {
      console.log(error);
      return [];
    }
  };

  const getOrderDetail = async () => {
    try {
      const response = await api.get(`/order/${orderID}`);
      console.log(response);
      return response.data;
    } catch (error) {
      console.log(error);
      return [];
    }
  };

  useEffect(() => {
    const handlePaymentStatus = async () => {
      if (vnp_TransactionStatus === "00") {
        await postOrderID();
        const orderDetails = await getOrderDetail();

        if (orderDetails && orderDetails.orderDetails) {
          const idsToRemove = orderDetails.orderDetails.map(
            (item) => item.koiId
          );
          console.log("IDs cần xóa:", idsToRemove); // Kiểm tra danh sách IDs
          dispatch(removeSelected(idsToRemove));
        }

        dispatch(clearOrder());
      } else {
        navigate("/error");
      }
    };

    handlePaymentStatus();
  }, [orderID, vnp_TransactionStatus, dispatch, navigate]);

  return (
    <div>
      <Result
        status="success"
        title="Thanh toán thành công!"
        subTitle="Order number: 2017182818828182881 Cloud server configuration takes 1-5 minutes, please wait."
        extra={[
          <Button
            type="primary"
            key="console"
            onClick={() => {
              navigate("/history");
            }}
          >
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
