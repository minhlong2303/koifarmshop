import React, { useState } from "react";
import { Menu, Button, Badge } from "antd";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./header.css";

function Header() {
  const [current, setCurrent] = useState("home");
  const cart = useSelector((store) => store.cart);
  const navigate = useNavigate();

  const handleClick = (e) => {
    setCurrent(e.key);
    // Điều hướng dựa trên giá trị của key
    if (e.key === "home") navigate("/");
    if (e.key === "shop") navigate("/shop");
    if (e.key === "contact") navigate("/contact");
    if (e.key === "about-us") navigate("/about-us");
  };

  return (
    <div className="navbar">
      <img src="" alt="" className="logo" />

      <Menu
        onClick={handleClick}
        selectedKeys={[current]}
        mode="horizontal"
        className="navbar-menu"
      >
        <Menu.Item key="home">Trang Chủ</Menu.Item>
        <Menu.Item key="about-us">Giới Thiệu</Menu.Item>
        <Menu.Item key="shop">Cá Koi</Menu.Item>
        <Menu.Item key="contact">Liên Hệ</Menu.Item>
      </Menu>

      <div className="navbar-right">
        <Button
          onClick={() => {
            navigate("cart");
          }}
          type="primary"
        >
          <Badge
            onClick={() => {
              navigate("/cart");
            }}
            count={cart.length}
          >
            <ShoppingCartOutlined style={{ fontSize: 20, color: "black" }} />
          </Badge>
        </Button>
        <Button
          onClick={() => navigate("/login")}
          type="primary"
          style={{ marginLeft: "16px" }}
        >
          Đăng nhập
        </Button>
      </div>
    </div>
  );
}

export default Header;
