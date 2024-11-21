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
      console.log("Order response:", response.data);

      // Lấy koiId và batchKoiId từ trường bên ngoài
      const koiIDs = response.data.koiId || [];
      const batchKoiIDs = response.data.batchKoiId || [];

      return { koiIDs, batchKoiIDs };
    } catch (error) {
      console.error("Error fetching order details:", error);
      return { koiIDs: [], batchKoiIDs: [] };
    }
  };

  const deleteItemsFromDatabase = async (koiIDs, batchKoiIDs) => {
    try {
      if (koiIDs.length > 0) {
        await Promise.all(koiIDs.map((id) => api.delete(`/koi/${id}`)));
        console.log("Deleted koi items:", koiIDs);
      }

      if (batchKoiIDs.length > 0) {
        await Promise.all(
          batchKoiIDs.map((id) => api.delete(`/batchKoi/${id}`))
        );
        console.log("Deleted batch koi items:", batchKoiIDs);
      }
    } catch (error) {
      console.error("Error in deleteItemsFromDatabase:", error);
    }
  };

  useEffect(() => {
    const handlePaymentStatus = async () => {
      if (vnp_TransactionStatus === "00") {
        await postOrderID();

        // Lấy danh sách koiId và batchKoiId
        const { koiIDs, batchKoiIDs } = await getOrderDetail();

        console.log("Koi IDs cần xóa:", koiIDs);
        console.log("Batch Koi IDs cần xóa:", batchKoiIDs);

        // Xóa sản phẩm trong database
        await deleteItemsFromDatabase(koiIDs, batchKoiIDs);

        // Dispatch action xóa các sản phẩm khỏi cart
        dispatch(removeSelected({ koiIDs, batchKoiIDs }));

        // Clear order
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
        subTitle="Cloud server configuration takes 1-5 minutes, please wait."
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
