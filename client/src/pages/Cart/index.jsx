import { Button, Image, Table } from "antd";
import "./index.css";
import { useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAll } from "../../redux/features/cartSlice";
import { useNavigate } from "react-router-dom";
import { clearOrder, createOrder } from "../../redux/features/orderSlice";
import { toast } from "react-toastify";

function CartPage() {
  const navigate = useNavigate();
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

  const totalAmount = useMemo(() => {
    return data.reduce((total, item) => total + item.price * item.quantity, 0);
  }, [data]);

  //Lưu ý chỗ này cần sửa nhiều để phù hợp với backend
  const handleBuy = async () => {
    try {
      const selectedItems = data.filter((koi) =>
        selectedRowKeys.includes(koi.iD)
      );
      if (selectedItems.length === 0) {
        dispatch(clearOrder());
        toast.warning("Bạn chưa chọn sản phẩm muốn mua");
        return;
      }
      dispatch(createOrder(selectedItems));
      navigate("/order");
    } catch (error) {
      console.log(error.response.data);
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
      title: "Price",
      dataIndex: "price",
      key: "price",
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Total Price",
      key: "totalPrice",
      render: (koiFish) => {
        return <span>{koiFish.price * koiFish.quantity}</span>;
      },
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
        pagination={false} // Không phân trang nếu bạn muốn hiển thị tất cả sản phẩm cùng một lúc
        footer={() => (
          <div style={{ textAlign: "right", fontSize: "18px" }}>
            <b>Total: </b> {totalAmount} {/* Hiển thị tổng số tiền */}
          </div>
        )}
      />
      <Button
        onClick={() => {
          handleBuy();
        }}
      >
        Mua ngay
      </Button>
    </div>
  );
}

export default CartPage;
