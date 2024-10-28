import React from "react";
import CRUDPictureTemplate from "../../../components/crud-template/CrudWithPicture";
import { Form, Input, InputNumber } from "antd";

function ManageBatchKoi() {
  const columns = [
    {
      label: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      label: "Image",
      dataIndex: "image",
      key: "image",
    },
    {
      label: "Price",
      dataIndex: "price",
      key: "price",
    },
    {
      label: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      label: "Quantity",
      dataIndex: "quantity",
      key: "quantity",
    },
  ];
  const formItem = (
    <>
      <Form.Item label="Name" name="name">
        <Input></Input>
      </Form.Item>
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
        label="Price / 1 Koi Fish"
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
      <Form.Item label="Description" name="description">
        <Input.TextArea />
      </Form.Item>
      <Form.Item
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
      </Form.Item>
    </>
  );
  return (
    <div>
      <CRUDPictureTemplate
        columns={columns}
        formItems={formItem}
        path="batchKoi"
      ></CRUDPictureTemplate>
    </div>
  );
}

export default ManageBatchKoi;
