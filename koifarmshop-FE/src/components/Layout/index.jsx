import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "../Header/header";
import AppFooter from "../footer";
import Header from "../Header/header";

function Layout() {
  return (
    <div>
      <div>
        <Header></Header>
        <div
          className="main-content"
          style={{ padding: 20, minHeight: "100vh" }}
        >
          <Outlet></Outlet>
        </div>
        <AppFooter></AppFooter>
      </div>
    </div>
  );
}

export default Layout;
