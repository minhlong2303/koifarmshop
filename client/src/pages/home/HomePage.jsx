/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import "./HomePage.css";

import KoiList from "../../components/KoiList";
import api from "../../config/axios";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";

function HomePage() {
  const [category, setCategory] = useState("All");
  const [products, setProducts] = useState([]);
  const fetchProduct = async () => {
    try {
      const response = await api.get("/koi");

      setProducts(response.data);
    } catch (e) {
      console.log("Error fetch product: ", e);
    }
  };

  useEffect(() => {
    fetchProduct();
  }, []);
  return (
    <>
      <div className="header">
        <img
          src="src/assets/z5887354582155_4aaf9324965efeb7a25ee98ce5cef5f2.jpg"
          alt=""
        />
      </div>
      <div>
        <KoiList category={category} setCategory={setCategory} />
        <div className="product-list">
          {products.map((product) => (
            <Product key={product.id} product={product} />
          ))}
        </div>
      </div>
    </>
  );
}
const Product = ({ product }) => {
  const dispatch = useDispatch();

  const handleAddToCart = () => {
    dispatch(addProduct(product));
  };
  return (
    <>
      <div className="product">
        <img src={product.image} alt="" />
        <h3>{product.name}</h3>
        <p>{product.description}</p>
        <span>{`${product.price} vnđ`}</span>
        <center>
          <button onClick={handleAddToCart}>Add to cart</button>
        </center>
      </div>
    </>
  );
};
export default HomePage;
