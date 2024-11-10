/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import "./index.scss";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { Image } from "antd";
import { useNavigate } from "react-router-dom";
function BatchKoiPage() {
  const [koiFishs, setKoiFishs] = useState([]);
  const fetchKoiFish = async () => {
    try {
      // const response = await api.get("koi");
      const response = await api.get("batchKoi");
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
  const navigate = useNavigate();

  return (
    <div
      className="product"
      onClick={() => navigate(`/batch-koi/${koiFish.batchKoiID}`)}
    >
      <Image src={koiFish.image} alt="koi's picture"></Image>
      <h3>{koiFish.name}</h3>
      <p>{koiFish.quantity}</p>
      <span>{`${koiFish.price} vnđ`}</span>
    </div>
  );
};

export default BatchKoiPage;
