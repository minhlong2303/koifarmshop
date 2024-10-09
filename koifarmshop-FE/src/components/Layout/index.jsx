import React from "react";
import Header from "../Header";
import { Outlet } from "react-router-dom";
import Navbar from "../navbar/navbar";
import AppFooter from "../footer";
import HeaderTest from "../headerTest";

function Layout() {
  return (
    <div>
      <div>
        <Navbar></Navbar>
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
