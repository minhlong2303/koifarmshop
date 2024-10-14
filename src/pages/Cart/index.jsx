/* eslint-disable no-unused-vars */
import { useDispatch, useSelector } from "react-redux";
import "./index.css"
import React, { useState } from 'react'
import { clearAll } from "../../redux/features/cartSlide";
import { toast } from "react-toastify";
import { Button, Image, Table } from "antd";
import api from "../../config/axios";

function Cart() {
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const data = useSelector((store) => store.cart);
  const dispatch = useDispatch();

  const onSelectChange = (newSelectedRowKeys) => {
    console.log("selectedRowKeys changed: ", newSelectedRowKeys);
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };

  const handleBuy = async () => {
    console.log(selectedRowKeys);
    console.log(data);

    //call API
    try {
      const selectedItems = data.filter((koi) =>
        selectedRowKeys.includes(koi.id)
      );

      console.log(selectedItems);

      const detail = selectedItems.map((koi) => ({
        koiId: koi.id,
        quantity: koi.quantity,
      }));
      const response = await api.post("order", { detail });
      dispatch(clearAll());
      toast.success("Successfully!");
    } catch (error) {
      toast.error("Failed to create order");
    }
  };

  const columns = [
    {
      title: "Image",
      dataIndex: "image",
      render: (image) => <Image width={200} src={image} />,
    },
    {
      title: "Name",
      dataIndex: "name",
    },
    {
      title: "description",
      dataIndex: "description",
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
    },
  ];
  return (
    <div
      style={{
        padding: "60px",
      }}
    >
      <Button onClick={() => dispatch(clearAll())}>Clear All</Button>
      <Table
        rowKey="id"
        rowSelection={rowSelection}
        columns={columns}
        dataSource={data}
      />

      <Button onClick={handleBuy}>Buy </Button>
    </div>
  );
}

export default Cart