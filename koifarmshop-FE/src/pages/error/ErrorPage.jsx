import React from "react";
import { Button, Result } from "antd";

function ErrorPage() {
  return (
    <div>
      {" "}
      <Result
        status="error"
        title="Submission Failed"
        subTitle="Please check and modify the following information before resubmitting."
        extra={[
          <Button type="primary" key="console">
            Go Console
          </Button>,
          <Button key="buy">Về trang chủ</Button>,
        ]}
      ></Result>
    </div>
  );
}

export default ErrorPage;
