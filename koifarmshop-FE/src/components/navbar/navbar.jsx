/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import "./navbar.css";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Badge } from "antd";
import { useSelector } from "react-redux";
function Navbar() {
  const [menu, setMenu] = useState("home");
  const cart = useSelector((store) => store.cart);
  return (
    <div className="navbar">
      <img src="" alt="" className="logo" />
      <ul className="navbar-menu">
        <li
          onClick={() => setMenu("Trang Chủ")}
          className={menu === "Trang Chủ" ? "active" : ""}
        >
          Trang Chủ
        </li>
        <li
          onClick={() => setMenu("Giới Thiệu")}
          className={menu === "Giới Thiệu" ? "active" : ""}
        >
          Giới Thiệu
        </li>
        <li
          onClick={() => setMenu("Cá Koi")}
          className={menu === "Cá Koi" ? "active" : ""}
        >
          Cá Koi
        </li>
        <li
          onClick={() => setMenu("Liên Hệ")}
          className={menu === "Liên Hệ" ? "active" : ""}
        >
          Liên Hệ
        </li>
        <li>
          <Badge count={cart.length}>
            <ShoppingCartOutlined
              size={100}
              style={{ fontSize: 20, color: "black" }}
            />
          </Badge>
        </li>
      </ul>
      <div className="navbar-right">
        <img src="" alt="" />
        <div className="navbar-serach-icon">
          <img src="" alt="" />
          <div className="dot"></div>
        </div>
        <button>Đăng nhập</button>
      </div>
    </div>
  );
}

export default Navbar;
