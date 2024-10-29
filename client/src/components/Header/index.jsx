/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import "./index.css";
import { AiOutlineSearch } from "react-icons/ai";
import { Badge, Button, Menu } from "antd";
import { AiOutlineUser } from "react-icons/ai";
import { AiOutlineShoppingCart } from "react-icons/ai";

import { Dropdown } from "antd";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../redux/features/userSlice";

function Header() {
  const user = useSelector((store) => store.user);
  const dispatch = useDispatch();
  const [current, setCurrent] = useState(null);
  const cart = useSelector((store) => store.cart);
  const navigate = useNavigate();
  const [menu, setMenu] = useState("home");
  const handleClick = (e) => {
    setCurrent(e.key);
    // Điều hướng dựa trên giá trị của key
    if (e.key === "home") navigate("/");
    if (e.key === "shop") navigate("/shop");
    if (e.key === "koiConsignment") navigate("/koiConsignment");
    if (e.key === "contact") navigate("/contact");
    if (e.key === "about-us") navigate("/about-us");
    if (e.key === "batchKoi") navigate("/batch-koi");
  };

  const items = () => {
    return user == null
      ? [
          {
            key: "2",
            label: <Link to="/login">Đăng nhập</Link>,
          },
          {
            key: "3",
            label: <Link to="/register">Đăng kí</Link>,
          },
        ]
      : [
          {
            key: "1",
            label: <Link to="/account-template">Tài khoản</Link>,
          },
          {
            key: "2",
            label: <Link to="/history">Lịch sử đơn hàng</Link>,
          },
          {
            key: "3",
            label: (
              <Button
                onClick={() => {
                  dispatch(logout());
                  navigate("login");
                }}
              >
                Đăng Xuất
              </Button>
            ),
          },
        ];
  };
  const userMenu = (
    <Menu items={items()} /> // items() sẽ trả về mảng kết quả
  );
  return (
    <>
      <div className="navbar">
        <img src="src/assets/logoo.jpg" alt="" className="logo" />
        <Menu
          onClick={handleClick}
          selectedKeys={[current]}
          mode="horizontal"
          className="navbar-menu"
        >
          <Menu.Item key="home">Trang Chủ</Menu.Item>

          <Menu.Item key="shop">Cá Koi</Menu.Item>
          <Menu.Item key="batchKoi">Mua theo lô</Menu.Item>
          <Menu.Item key="koiConsignment">Kí gửi-Bán hộ</Menu.Item>
          <Menu.Item key="about-us">Giới Thiệu</Menu.Item>
          <Menu.Item key="contact">Liên Hệ</Menu.Item>
        </Menu>
        <div className="navbar-right">
          <Button className="search-icon">
            <AiOutlineSearch size={25} />
          </Button>
          <Dropdown overlay={userMenu} trigger={["click"]}>
            <Button className="user-icon">
              <AiOutlineUser size={25} />
            </Button>
          </Dropdown>
          <Button onClick={() => navigate("/cart")} className="cart-icon">
            <Badge count={cart.length}>
              <AiOutlineShoppingCart size={25} />
            </Badge>
          </Button>
        </div>
      </div>
    </>
  );
}

export default Header;
