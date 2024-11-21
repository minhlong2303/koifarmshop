import { Button, Result } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";

function ConsignmentSuccess() {
  const navigate = useNavigate();
  return (
    <div>
      <Result
        status="success"
        title="Bạn đã gửi đơn ký gửi thành công"
        // subTitle="Order number: 2017182818828182881 Cloud server configuration takes 1-5 minutes, please wait."
        extra={[
          <Button type="primary" key="console">
            Go Console
          </Button>,
          <Button
            onClick={() => {
              navigate("/");
            }}
            key="buy"
          >
            Về Trang Chủ
          </Button>,
        ]}
      />
    </div>
  );
}

export default ConsignmentSuccess;
