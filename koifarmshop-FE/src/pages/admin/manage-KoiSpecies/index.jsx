import React from "react";
import { Form, Input } from "antd";
import CRUDTemplate from "../../../components/crud-template/CrudWithoutPicture";

function ManageKoiSpecies() {
  const columns = [
    {
      title: "Koi Species ID",
      dataIndex: "SpeciesID",
      key: "SpeciesID",
    },
    {
      title: "Koi Species Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
  ];

  const formItems = (
    <>
      <Form.Item label="ID" name="SpeciesID" hidden>
        <Input />
      </Form.Item>
      <Form.Item
        label="Name"
        name="name"
        rules={[
          {
            required: true,
            message: "Please enter Species's name",
          },
          {
            max: 50,
            message: "Name must be less than 50 characters",
          },
          {
            pattern: /^[a-zA-Z0-9 ]+$/,
            message: "Name should only contain letters and numbers",
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Description"
        name="description"
        rules={[
          {
            min: 10,
            message: "Description must be at least 10 characters",
          },
          {
            max: 500,
            message: "Description cannot exceed 500 characters",
          },
        ]}
      >
        <Input.TextArea />
      </Form.Item>
    </>
  );

  return (
    <div>
      <CRUDTemplate
        columns={columns}
        formItems={formItems}
        path="koi-species"
      />
    </div>
  );
}

export default ManageKoiSpecies;
