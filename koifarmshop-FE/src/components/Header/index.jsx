/* eslint-disable no-unused-vars */
import React from "react";
import "./index.css";
function Header() {
  return (
    <div className="header">
      <div className="header-contents">
        <h2>Order your favourite food here</h2>
        <p>
          Choose from a diverse menu featuring a delectable of array dishes
          crafted with the
        </p>
        <button>View Menu</button>
      </div>
      <div className="header-image">
        <img src="src/assets/koi.jpg" alt="" />
      </div>
    </div>
  );
}

export default Header;
