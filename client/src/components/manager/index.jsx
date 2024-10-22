import { useState } from "react";
import { IoFishOutline } from "react-icons/io5";
import { IoIosList } from "react-icons/io";
import {
  PieChartOutlined,
  ShopOutlined,
  TeamOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu } from "antd";
import { Link, Outlet } from "react-router-dom";
import Header from "../Header";

const { Content, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const items = [
  getItem("Sản phẩm", "manageShop", <ShopOutlined />, [
    getItem(
      <Link to="/manager/KoiSpecies">Giống cá Koi</Link>,
      "KoiSpecies",
      <PieChartOutlined />
    ),
    getItem(<Link to="/manager/koi">Cá Koi</Link>, "koi", <IoFishOutline />),
  ]),
  getItem("Quản Lí", "manage", <TeamOutlined />, [
    getItem(
      <Link to="/manager/users">Khách hàng</Link>,
      "users",
      <IoIosList />
    ),
    getItem(
      <Link to="/manager/manage-orders">Đơn hàng</Link>,
      "manage-orders",
      <IoIosList />
    ),
  ]),
  getItem("Dashboard & Report", "dashboardReport", <TeamOutlined />, [
    getItem("Team 1", "6"),
    getItem("Team 2", "8"),
  ]),
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

const layoutStyle = {
  borderRadius: 8,
  overflow: "hidden",
  width: "100%",
  height: "100vh",
};
const Manager = () => {
  const [collapsed, setCollapsed] = useState(false);
  // const {
  //   token: { colorBgContainer, borderRadiusLG },
  // } = theme.useToken();

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
    </Layout>
  );
};

export default Manager;
