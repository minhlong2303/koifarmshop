import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { Image } from "antd";
import "./ProductDetailPage.scss";
import api from "../../config/axios";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";

function ProductDetailPage() {
  const dispatch = useDispatch();
  const { koiID } = useParams();
  const [koiFish, setKoiFish] = useState(null);

  const fetchKoiFishDetail = async () => {
    try {
      const response = await api.get(`/koi/${koiID}`);
      setKoiFish(response.data);
    } catch (error) {
      console.log("Failed to fetch product details:", error);
    }
  };

  useEffect(() => {
    fetchKoiFishDetail();
  }, []);

  const handleAddToCart = (koiFish) => {
    dispatch(addProduct(koiFish));
  };

  if (!koiFish) return <p>Loading...</p>;

  return (
    <div className="product-detail">
      <Image src={koiFish.image} alt={koiFish.name} />
      <h1>{koiFish.name}</h1>
      <p>{koiFish.description}</p>
      <span>{`${koiFish.price} vnđ`}</span>
      <button onClick={() => handleAddToCart(koiFish)}>
        Thêm vào giỏ hàng
      </button>
    </div>
  );
}

export default ProductDetailPage;
