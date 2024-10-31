import { Descriptions, Image } from "antd";

import ManageTemplate from "../../../components/manage-template";

const ManageOrder = () => {
  const columns = [
    {
      title: "OrderID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
      render: (price) => `${price.toLocaleString()} VND`, // Format lại giá nếu cần
      sorter: (a, b) => a.price - b.price,
    },
  ];
  const userDetail = ({ record }) => (
    <Descriptions bordered layout="vertical">
      <Descriptions.Item label="ID">{record.id}</Descriptions.Item>
      <Descriptions.Item label="Name">
        {record.firstName} {record.lastName}
      </Descriptions.Item>
      <Descriptions.Item label="Phone Number">{record.phone}</Descriptions.Item>
      <Descriptions.Item label="Email">{record.email}</Descriptions.Item>
      <Descriptions.Item label="Image">
        <Image src={record.profileIMG} alt="User's picture" width={100}></Image>
      </Descriptions.Item>
    </Descriptions>
  );

  return (
    <div>
      <ManageTemplate
        columns={columns}
        DescriptionsForm={userDetail}
        path="order"
        idKey="orderID"
      ></ManageTemplate>
    </div>
  );
};

export default ManageOrder;
