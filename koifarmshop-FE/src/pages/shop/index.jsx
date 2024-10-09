/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import Navbar from "../../components/navbar/navbar";
import api from "../../config/axios";
import "./index.scss";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
function ShopPage() {
  const [koiFishs, setKoiFishs] = useState([]);
  const fetchKoiFish = async () => {
    try {
      // const response = await api.get("koi");
      const response = await api.get("koi/all");
      setKoiFishs(response.data.content);
    } catch (error) {
      console.log(error.response.data);
    }
  };
  return (
    <div>
      {/*koiFishs => <Product></Product> */}
      <div className="product-list">
        {koiFishs.map((koiFish) => (
          // eslint-disable-next-line react/jsx-key
          <Product product={koiFish} />
        ))}
      </div>
    </div>
  );
}
const Product = ({ koiFish }) => {
  const dispatch = useDispatch();
  const handleAddToCart = () => {
    dispatch(addProduct(koiFish));
  };
  return (
    <div className="product">
      <img
        src="https://firebasestorage.googleapis.com/v0/b/koifarmshop-6cae6.appspot.com/o/images.jpg?alt=media&token=6df342a7-cb7e-4d14-8e93-9ffcb23ed38e"
        alt="koi's picture"
      ></img>
      {/*Khi nào koi có image sẽ thay vô */}
      {/* <img src={koiFish.image} alt="koi's picture"></img> */}
      <h3>{koiFish.name}</h3>
      <p>{koiFish.description}</p>
      <span>{koiFish.price}</span>
      <center>
        <button onClick={handleAddToCart}>Add to cart</button>
      </center>
    </div>
  );
};

export default ShopPage;
