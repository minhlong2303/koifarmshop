/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import "./index.scss";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { Image } from "antd";
function ShopPage() {
  const [koiFishs, setKoiFishs] = useState([]);
  const fetchKoiFish = async () => {
    try {
      // const response = await api.get("koi");
      const response = await api.get("koi");
      setKoiFishs(response.data);
      //Bên back-end là response.data.content
    } catch (error) {
      console.log(error.response.data);
    }
  };
  useEffect(() => {
    fetchKoiFish();
  }, []);
  return (
    <div>
      {/*koiFishs => <Product></Product> */}
      <div className="product-list">
        {koiFishs.map((koiFish) => (
          // eslint-disable-next-line react/jsx-key
          <Product key={koiFish.koiID} koiFish={koiFish} />
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
      <Image src={koiFish.image} alt="koi's picture"></Image>
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
