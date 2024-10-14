/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import "./index.css";
import { AiOutlineSearch } from "react-icons/ai";
import { Badge, Button } from "antd";
import { AiOutlineUser } from "react-icons/ai";
import { AiOutlineShoppingCart } from "react-icons/ai";

import { Dropdown } from "antd";
import { Link, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

function Header() {
  const cart = useSelector((store) => store.cart);
  const navigate = useNavigate();
  const [menu, setMenu] = useState("home");
  const items = [
    {
      key: 1,
      label: <Link to="/">Tài khoản</Link>,
    },
    {
      key: "2",
      label: <Link to="/login">Đăng nhập</Link>,
    },
    {
      key: "3",
      label: <Link to="/register">Đăng kí</Link>,
    },
  ];
  return (
    <>
      <div className="navbar">
        <img src="src/assets/logoo.jpg" alt="" className="logo" />
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
        </ul>
        <div className="navbar-right">
          <Button className="search-icon">
            <AiOutlineSearch size={25} />
          </Button>
          <Dropdown
            menu={{
              items,
            }}
          >
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
      <div className="header">
        <img
          src="src/assets/z5887354582155_4aaf9324965efeb7a25ee98ce5cef5f2.jpg"
          alt=""
        />
      </div>
    </>
  );
}

export default Header;
