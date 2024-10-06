/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import "./navbar.css";

function Navbar() {
  const [menu, setMenu] = useState("home");
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
