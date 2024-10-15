/* eslint-disable no-unused-vars */
import React from "react";

import { Outlet } from "react-router-dom";
import Header from "../Header";
import AppFooter from "../footer";

function Layout() {
  return (
    <div>
      <Header />
      <div
        className="main-content"
        style={{
          padding: 20,
          minHeight: "100vh",
        }}
      >
        <Outlet />
      </div>
      <AppFooter></AppFooter>
    </div>
  );
}

export default Layout;
