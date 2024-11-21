/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import "./index.scss";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { Image, message } from "antd";
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
          <Product key={koiFish.batchKoiID} koiFish={koiFish} />
        ))}
      </div>
    </div>
  );
}

const Product = ({ koiFish }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleAddToCart = () => {
    if (koiFish.quantity <= 0) {
      message.error("Sản phẩm này đã hết hàng!");
      return;
    }

    const productWithQuantity = {
      ...koiFish,
      quantity: koiFish.quantity, // Mặc định thêm số lượng là 1
    };
    dispatch(addProduct(productWithQuantity));
    message.success("Đã thêm sản phẩm vào giỏ hàng!");
  };
  return (
    <div className="product">
      <div onClick={() => navigate(`/batch-koi/${koiFish.batchKoiID}`)}>
        <Image src={koiFish.image} alt="koi's picture"></Image>
        <h3>{koiFish.name}</h3>
        <p>{koiFish.quantity}</p>
        <span>{`${koiFish.price} vnđ`}</span>
      </div>
      <center>
        <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
      </center>
    </div>
  );
};

export default BatchKoiPage;
