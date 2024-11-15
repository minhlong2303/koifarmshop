import { Button, Image, Table } from "antd";
import "./index.css";
import { useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { clearAll, removeFromCart } from "../../redux/features/cartSlice";
import { useNavigate } from "react-router-dom";
import { clearOrder, createOrder } from "../../redux/features/orderSlice";
import { toast } from "react-toastify";

function CartPage() {
  const user = useSelector((store) => store.user);
  const cart = useSelector((store) => store.cart);
  const navigate = useNavigate();
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const data = useSelector((store) => store.cart);
  const dispatch = useDispatch();

  useEffect(() => {
    if (user) {
      localStorage.setItem(`cart_${user.id}`, JSON.stringify(cart));
    }
  }, [cart, user]);

  if (!user || !user.token) {
    return (
      <div style={{ padding: "60px", textAlign: "center" }}>
        Giỏ hàng trống. Vui lòng đăng nhập để thêm sản phẩm.
      </div>
    );
  }

  const onSelectChange = (newSelectedRowKeys) => {
    setSelectedRowKeys(newSelectedRowKeys);
  };
  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };

  const totalAmount = useMemo(() => {
    return data.reduce((total, item) => total + item.price * item.quantity, 0);
  }, [data]);

  const handleBuy = async () => {
    try {
      const selectedItems = data.filter(
        (item) =>
          selectedRowKeys.includes(item.koiID) ||
          selectedRowKeys.includes(item.batchKoiID)
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
      title: "Ảnh",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="" width={100}></Image>;
      },
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "name",
      key: "name",
      render: (_, item) =>
        item.koiID ? `Cá Koi: ${item.name}` : `Lô cá Koi: ${item.name}`,
    },
    {
      title: "Giá",
      dataIndex: "price",
      key: "price",
      render: (price) => `${price} vnđ`,
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Tổng tiền",
      key: "totalPrice",
      render: (item) => {
        return <span>{item.price * item.quantity} vnđ</span>;
      },
    },
    {
      title: "Action",
      key: "action",
      render: (_, item) => (
        <Button type="primary" danger onClick={() => handleRemove(item)}>
          Xóa
        </Button>
      ),
    },
  ];
  const handleRemove = (item) => {
    dispatch(removeFromCart(item));
    toast.success("Đã xóa sản phẩm khỏi giỏ hàng");
  };

  return (
    <div style={{ padding: "60px" }}>
      <Button onClick={() => dispatch(clearAll())}>Xóa tất cả</Button>
      <Table
        rowKey={(record) => record.koiID || record.batchKoiID}
        rowSelection={rowSelection}
        columns={columns}
        dataSource={data}
        pagination={false}
        footer={() => (
          <div style={{ textAlign: "right", fontSize: "18px" }}>
            <b>Tổng: </b> {totalAmount} vnđ
          </div>
        )}
      />
      <Button onClick={handleBuy}>Mua ngay</Button>
    </div>
  );
}

export default CartPage;
