/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import "./index.scss";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { Image } from "antd";
import { useNavigate } from "react-router-dom";
function ShopPage() {
  const [koiFishs, setKoiFishs] = useState([]);
  const fetchKoiFish = async () => {
    try {
      // const response = await api.get("koi");
      const response = await api.get("/koi");
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
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleAddToCart = () => {
    dispatch(addProduct({ ...koiFish, quantity: 1 }));
  };
  const handleViewDetail = () => {
    navigate(`/detail/${koiFish.koiID}`);
  };
  return (
    <div className="product">
      <div onClick={handleViewDetail}>
        <Image src={koiFish.image} alt="koi's picture"></Image>
        {/*Khi nào koi có image sẽ thay vô */}
        {/* <img src={koiFish.image} alt="koi's picture"></img> */}
        <h3>{koiFish.name}</h3>
        <p>{koiFish.description}</p>
        <span>{`${koiFish.price} vnđ`}</span>
      </div>
      {/* <Image src={koiFish.image} alt="koi's picture"></Image>
      <h3>{koiFish.name}</h3>
      <p>{koiFish.description}</p>
      <span>{`${koiFish.price} vnđ`}</span> */}
      <center>
        <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
      </center>
    </div>
  );
};

export default ShopPage;
