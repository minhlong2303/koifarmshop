import { Image, message } from "antd";
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { useParams } from "react-router-dom";

function BatchKoiDetail() {
  const [batchKoiFish, setBatchKoiFish] = useState(null);
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();
  const { batchKoiID } = useParams();

  const fetchBatchKoiFishDetail = async () => {
    try {
      const response = await api.get(`batchKoi/${batchKoiID}`);
      setBatchKoiFish(response.data);
    } catch (error) {
      console.log("Failed to fetch product details:", error);
    }
  };

  useEffect(() => {
    fetchBatchKoiFishDetail();
  }, []);

  const handleAddToCart = () => {
    if (batchKoiFish.quantity <= 0) {
      message.error("Sản phẩm này đã hết hàng!");
      return;
    }

    setLoading(true);
    const productWithQuantity = {
      ...batchKoiFish,
      quantity: batchKoiFish.quantity, // Mặc định thêm số lượng là 1
    };
    dispatch(addProduct(productWithQuantity));
    message.success("Đã thêm sản phẩm vào giỏ hàng!");
    setLoading(false);
  };

  if (!batchKoiFish) {
    return <div>Loading...</div>;
  }

  return (
    <div className="product-detail">
      <Image src={batchKoiFish.image} alt={batchKoiFish.name} />
      <h1>{batchKoiFish.name}</h1>
      <p>Số lượng: {batchKoiFish.quantity}</p>
      <p>Mô tả: {batchKoiFish.description}</p>
      <span>{`${batchKoiFish.price} vnđ`}</span>
      <button onClick={handleAddToCart} disabled={loading}>
        Thêm vào giỏ hàng
      </button>
    </div>
  );
}

export default BatchKoiDetail;
