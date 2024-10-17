import { Button, Image, Table } from "antd";
import "./index.css";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAll } from "../../redux/features/cartSlice";
import api from "../../config/axios";
import { toast } from "react-toastify";

function CartPage() {
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const data = useSelector((store) => store.cart);
  const dispatch = useDispatch();
  console.log(data);
  const onSelectChange = (newSelectedRowKeys) => {
    console.log("selectedRowKeys changed: ", newSelectedRowKeys);
    setSelectedRowKeys(newSelectedRowKeys);
  };
  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };

  //Lưu ý chỗ này cần sửa nhiều để phù hợp với backend
  const handleBuy = async () => {
    try {
      const selectedItems = data.filter((koi) =>
        selectedRowKeys.includes(koi.koiID)
      );

      const detail = selectedItems.map((koi) => ({
        koiId: koi.koiID,
        quantity: koi.quantity,
      }));
      const response = await api.post("order", { detail });
      // dispatch(clearAll()); //Cái này dùng để xóa Item mình vừa mới mua nào học xong sẽ update tiếp
      window.open(response.data);
      toast.success("Create Order successfully");
    } catch (error) {
      console.log(error.response.data);
      toast.error("Failed to create order");
    }
  };

  const columns = [
    {
      title: "Image",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="" width={100}></Image>;
      },
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
      key: "quantity",
    },
  ];

  return (
    <div
      style={{
        padding: "60px",
      }}
    >
      <Button onClick={() => dispatch(clearAll())}>Clear all</Button>
      <Table
        rowKey="koiID"
        rowSelection={rowSelection}
        columns={columns}
        dataSource={data}
      />
      <Button onClick={handleBuy}>Buy</Button>
    </div>
  );
}

export default CartPage;
