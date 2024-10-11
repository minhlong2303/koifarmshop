/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import "./index.css";

function HomePage() {
  return (
    <div className="homepage">
      <div className="homepage-contents">
        <h2>Order your favourite food here</h2>
        <p>
          Choose from a diverse menu featuring a delectable of array dishes
          crafted with the
        </p>
        <button>View Menu</button>
      </div>
      <div className="homepage-image">
        <img src="src/assets/koi.jpg" alt="" />
      </div>
    </div>
  );
}

export default HomePage;
