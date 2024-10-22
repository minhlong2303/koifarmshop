import { Button, Result } from "antd";
import React from "react";
import { useSelector } from "react-redux";

function ProtectedRoute({ role, children }) {
  const user = useSelector((store) => store.user);
  if (user && user.role === role) {
    return children;
  }
  return (
    <div>
      <Result
        status="403"
        title="403"
        subTitle="Sorry, you are not authorized to access this page."
        extra={<Button type="primary">Back Home</Button>}
      />
    </div>
  );
}

export default ProtectedRoute;
