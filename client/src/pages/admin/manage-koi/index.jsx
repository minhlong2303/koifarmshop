import { Form, Input, InputNumber } from "antd";
import CRUDPictureTemplate from "../../../components/crud-template/CrudWithPicture";

function ManageKoi() {
  const columns = [
    { title: "ID", dataIndex: "koiID", key: "koiID" },
    { title: "Name", dataIndex: "name", key: "name" }, //koiName => name
    {
      title: "Image",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="KoiFish's picture" width={100} />;
      },
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
      render: (price) => `${price} $`,
    },
    // { title: "Quantity", dataIndex: "quantity", key: "quantity" },
  ];

  const formItems = (
    <>
      <Form.Item
        label="Koi Fish's name"
        name="name"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's name",
          },
          {
            min: 3,
            message: "Name should be at least 3 characters",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Price"
        name="price"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's price",
          },
          {
            type: "number",
            min: 1,
            max: 99999999,
            message: "Price must be between 1 and 10,000",
          },
        ]}
      >
        <InputNumber />
      </Form.Item>

      {/* <Form.Item
        label="Quantity"
        name="quantity"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's quantity",
          },
          {
            type: "number",
            min: 1,
            max: 100,
            message: "Quantity must be between 1 and 100",
          },
        ]}
      >
        <InputNumber />
      </Form.Item> */}

      <Form.Item
        label="Koi Fish's speciesID"
        name="speciesId"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's speciesID",
          },
          {
            type: "number",
            min: 1,
            max: 999,
            message: "SpeciesID must be between 1 and 999",
          },
        ]}
      >
        <InputNumber />
      </Form.Item>

      <Form.Item
        label="Koi Fish's origin"
        name="origin"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's origin",
          },
          {
            min: 3,
            message: "Origin should be at least 3 characters",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Koi Fish's gender"
        name="gender"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's gender",
          },
          {
            pattern: new RegExp(/^(Male|Female)$/i),
            message: "Gender must be either 'Male' or 'Female'",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Koi Fish's size"
        name="size"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's size",
          },
          {
            type: "number",
            min: 1,
            max: 120,
            message: "Size must be between 1 and 120 cm",
          },
        ]}
      >
        <InputNumber />
      </Form.Item>

      <Form.Item
        label="Koi Fish's breed"
        name="breed"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's breed",
          },
          {
            min: 3,
            message: "Breed should be at least 3 characters",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Koi Fish's location"
        name="location"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's location",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Koi Fish's owner"
        name="owner"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's owner",
          },
          {
            min: 3,
            message: "Owner's name should be at least 3 characters",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Koi Fish's description"
        name="description"
        rules={[
          {
            min: 10,
            message: "Description should be at least 10 characters",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item label="Koi Fish's Id" name="koiID" hidden>
        <Input />
      </Form.Item>
    </>
  );

  return (
    <CRUDPictureTemplate columns={columns} formItems={formItems} path="koi" />
  );
}

export default ManageKoi;
