import { useState } from "react";
import {
  EnvironmentOutlined,
  ShoppingOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu } from "antd";
import { Link, Outlet } from "react-router-dom";
import Header from "../Header";

const { Content, Footer, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const items = [
  getItem(
    <Link to="/account-template/users">Tài khoản</Link>,
    "users",
    <UserOutlined />
  ),
  // getItem(
  //   <Link to="/account-template/address">Địa chỉ</Link>,
  //   "address",
  //   <EnvironmentOutlined />
  // ),
  getItem(
    <Link to="/account-template/cart">Đơn hàng</Link>,
    "cart",
    <ShoppingOutlined />
  ),
  getItem(
    <Link to="/account-template/consignment">Đơn Ký Gửi</Link>,
    "consignment",
    <ShoppingOutlined />
  ),
];

const contentStyle = {
  textAlign: "center",
  minHeight: 120,

  lineHeight: "120px",
  color: "#fff",
  backgroundColor: "#fff",
};

const siderStyle = {
  textAlign: "center",
  lineHeight: "120px",
  color: "#fff",
  backgroundColor: "#fdecee",
};

const footerStyle = {
  textAlign: "center",
  color: "#fff",
  backgroundColor: "#fdecee",
};

const layoutStyle = {
  borderRadius: 8,
  overflow: "hidden",
  width: "100%",
  height: "100vh",
};

const AccountTemplate = () => {
  const [collapsed, setCollapsed] = useState(false);
  //   const {
  //     token: { colorBgContainer, borderRadiusLG },
  //   } = theme.useToken();

  return (
    <Layout style={layoutStyle}>
      {/* Sử dụng Header từ thành phần bên ngoài */}
      <Header />

      <Layout>
        <Sider
          collapsible
          collapsed={collapsed}
          onCollapse={(value) => setCollapsed(value)}
          width="15%"
          style={siderStyle}
        >
          <Menu
            theme="light"
            defaultSelectedKeys={["1"]}
            mode="inline"
            items={items}
          />
        </Sider>

        <Content style={contentStyle}>
          <Breadcrumb style={{ margin: "16px 0" }}></Breadcrumb>
          <div style={{ minHeight: 360 }}>
            <Outlet />
          </div>
        </Content>
      </Layout>

      <Footer style={footerStyle}>Footer</Footer>
    </Layout>
  );
};

export default AccountTemplate;
