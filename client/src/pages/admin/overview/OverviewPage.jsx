import { Card, Col, Row, Statistic, Tooltip } from "antd";
import React, { useEffect, useState } from "react";
import { ArrowDownOutlined, ArrowUpOutlined } from "@ant-design/icons";
import api from "../../../config/axios";
import {
  Bar,
  BarChart,
  CartesianGrid,
  Cell,
  Legend,
  Pie,
  PieChart,
  XAxis,
  YAxis,
} from "recharts";

function OverviewPage() {
  const [data, setData] = useState();
  const [monthlyData, setMonthlyData] = useState();
  const fetchData = async () => {
    try {
      const response = await api.get("/manager/stats");
      console.log(response.data);
      setData(response.data);
    } catch (error) {
      console.log(error);
    }
  };
  const fetchDataMonthly = async () => {
    try {
      const response = await api.get("/manager/revenue/monthly");
      console.log(response.data.monthlyRevenue);
      const formatData = response.data.monthlyRevenue.map((item) => ({
        name: `${item?.month}/${item?.year}`,
        Revenue: item?.totalRevenue,
      }));
      setMonthlyData(formatData);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    fetchData();
    fetchDataMonthly();
  }, []);

  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];
  return (
    <div>
      <Row gutter={16}>
        <Col span={8}>
          <Card bordered={false}>
            <Statistic
              title="Total Product"
              value={data?.totalProducts}
              valueStyle={{
                color: "#3f8600",
              }}
              //   prefix={<ArrowUpOutlined />} //icon
              suffix="sản phẩm"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card bordered={false}>
            <Statistic
              title="Total Owner"
              value={data?.ownerCount}
              valueStyle={{
                color: "#3f8600",
              }}
              //   prefix={<ArrowUpOutlined />} //icon
              suffix=""
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card bordered={false}>
            <Statistic
              title="Total Customer"
              value={data?.customerCount}
              valueStyle={{
                color: "#3f8600",
              }}
              //   prefix={<ArrowUpOutlined />} //icon
              suffix=""
            />
          </Card>
        </Col>
      </Row>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          marginTop: 20,
          marginBottom: 20,
          alignItems: "center",
        }}
      >
        <PieChart width={400} height={200}>
          <Pie
            data={data?.topProduct}
            dataKey="totalSold"
            nameKey="Kohaku"
            cx="50%"
            cy="50%"
            outerRadius={60}
            fill="#8884d8"
          >
            {data?.topProduct?.map((item, index) => (
              <Cell key={index} fill={COLORS[index]}></Cell>
            ))}
          </Pie>
        </PieChart>
        <BarChart width={500} height={250} data={monthlyData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="Revenue" fill="#8884d8" />
        </BarChart>
      </div>
    </div>
  );
}

export default OverviewPage;
