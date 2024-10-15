import React from "react";
import CRUDTemplate from "../../../components/crud-template/CrudWithPicture";
import { Form, Image, Input, InputNumber } from "antd";

function ManageServiceGroup() {
  const columns = [
    { title: "ID", dataIndex: "id", key: "id" },
    { title: "Name", dataIndex: "name", key: "id" },
    {
      title: "Image",
      dataIndex: "image",
      key: "image",
      render: (image) => {
        return <Image src={image} alt="KoiFish's pircture" width={200}></Image>;
      },
    },
    { title: "Price", dataIndex: "price", key: "price" },
    { title: "Quantity", dataIndex: "quantity", key: "quantity" },
  ];
  const formItems = (
    <>
      <Form.Item
        label="Koi Fish's name"
        name="koiName"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fishh's name",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Price"
        name="price"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's speciesID",
          },
          {
            type: "number",
            min: 0,
            max: 20,
            message: "Invalid code",
          },
        ]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item
        label="Quantity"
        name="quantity"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's speciesID",
          },
          {
            type: "number",
            min: 0,
            max: 20,
            message: "Invalid code",
          },
        ]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item
        label="Koi Fish's speciesID"
        name="koiSpeciesID"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's speciesID",
          },
          {
            type: "number",
            min: 0,
            max: 20,
            message: "Invalid code",
          },
        ]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item
        label="Koi Fish's origin"
        name="koiOrigin "
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's origin",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's gender"
        name="koiGender"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's gender",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's Age"
        name="koiAge"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's age",
          },
          {
            type: "number",
            min: 1,
            max: 20,
            message: "Invalid age ",
          },
        ]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item
        label="Koi Fish's size"
        name="koiSize"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's size",
          },
          {
            type: "number",
            min: 1,
            max: 120,
            message: "Invalid size",
          },
        ]}
      >
        <InputNumber></InputNumber>
      </Form.Item>
      <Form.Item
        label="Koi Fish's breed"
        name="koiBreed"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's breed",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's location"
        name="location"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's Location",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's owner"
        name="owner"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's owner",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's description"
        name="description"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's description",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
      <Form.Item
        label="Koi Fish's Id"
        name="koiId"
        rules={[
          {
            required: true,
            message: "Please enter Koi Fish's Id",
          },
        ]}
      >
        <Input></Input>
      </Form.Item>
    </>
  );

  return <CRUDTemplate columns={columns}></CRUDTemplate>;
}

export default ManageServiceGroup;
