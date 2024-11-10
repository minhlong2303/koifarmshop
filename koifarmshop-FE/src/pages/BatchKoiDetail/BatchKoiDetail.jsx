import { Form, Image, InputNumber, Modal, message } from "antd";
import React, { useEffect, useState } from "react";
import api from "../../config/axios";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { useParams } from "react-router-dom";
import { useForm } from "antd/es/form/Form";

function BatchKoiDetail() {
  const [batchKoiFish, setBatchKoiFish] = useState(null);
  const [openModal, setOpenModal] = useState(null);
  const [form] = useForm();
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

  const handleAddToCart = (values) => {
    setLoading(true);
    if (values.quantity > batchKoiFish.quantity) {
      message.error("Số lượng nhập vào vượt quá số lượng có sẵn!");
      setLoading(false);
      return;
    }

    const productWithQuantity = {
      ...batchKoiFish,
      quantity: values.quantity,
    };
    dispatch(addProduct(productWithQuantity));
    setLoading(false);
    setOpenModal(false);
    form.resetFields();
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
      <button onClick={() => setOpenModal(true)}>Thêm vào giỏ hàng</button>
      <Modal
        open={openModal}
        onCancel={() => setOpenModal(false)}
        onOk={() => form.submit()}
        title="Vui lòng điền số lượng"
        confirmLoading={loading}
      >
        <Form form={form} labelCol={{ span: 24 }} onFinish={handleAddToCart}>
          <Form.Item
            label="Số lượng"
            name="quantity"
            rules={[
              {
                required: true,
                message: "Vui lòng điền số lượng",
              },
              {
                type: "number",
                min: 1,
                message: "Số lượng phải lớn hơn 0",
              },
            ]}
          >
            <InputNumber
              min={1}
              onBlur={() => form.validateFields(["quantity"])}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default BatchKoiDetail;
