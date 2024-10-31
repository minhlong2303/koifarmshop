import { Descriptions, Image } from "antd";

import ManageTemplate from "../../../components/manage-template";

const ManageUser = () => {
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Name",
      dataIndex: ["firstName", "lastName"],
      key: "name",
      render: (text, record) => `${record.firstName} ${record.lastName}`,
      sorter: (a, b) => a.firstName.localeCompare(b.firstName),
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
        path="user"
        idKey="id"
      ></ManageTemplate>
    </div>
  );
};

export default ManageUser;
