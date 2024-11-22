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
  const [loading, setLoading] = useState(true); // Thêm trạng thái loading

  const fetchKoiFish = async () => {
    try {
      const response = await api.get("batchKoi");
      setKoiFishs(response.data);
    } catch (error) {
      console.log(error.response.data);
    } finally {
      setLoading(false); // Dừng loading sau khi fetch xong
    }
  };

  useEffect(() => {
    fetchKoiFish();
  }, []);

  return (
    <div>
      {loading ? ( // Hiển thị trạng thái loading
        <p>Đang tải dữ liệu...</p>
      ) : koiFishs.length === 0 ? ( // Kiểm tra nếu danh sách trống
        <p>Không có lô cá Koi nào hiện tại!</p>
      ) : (
        <div className="product-list">
          {koiFishs.map((koiFish) => (
            <Product key={koiFish.batchKoiID} koiFish={koiFish} />
          ))}
        </div>
      )}
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
        <Image src={koiFish.image} alt="koi's picture" />
        <h3>{`Lô: ${koiFish.name}`}</h3>
        <p>{`Số lượng: ${koiFish.quantity}`}</p>
        <span>{`${koiFish.price} vnđ`}</span>
      </div>
      <center>
        <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
      </center>
    </div>
  );
};

export default BatchKoiPage;
