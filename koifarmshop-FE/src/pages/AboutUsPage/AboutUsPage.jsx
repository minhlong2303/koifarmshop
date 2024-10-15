import React from "react";
import { Card, Row, Col, Typography } from "antd";
import "./aboutUs.css";

const { Title, Paragraph } = Typography;

const AboutUs = () => {
  return (
    <div className="about-us-container">
      <Row gutter={[16, 16]} justify="center" align="middle">
        <Col xs={24} md={18}>
          <Card className="about-us-card" bordered={false}>
            <Title level={2}>About Us</Title>
            <Paragraph>
              Welcome to our Koi Fish business! We specialize in breeding and
              selling premium Koi fish, providing our customers with the highest
              quality fish for their ponds. Our expert team ensures that each Koi
              is healthy, vibrant, and raised in a pristine environment.
            </Paragraph>
            <Paragraph>
              Our mission is to bring tranquility and beauty to your pond with
              our stunning collection of Koi fish. Whether you are a beginner or
              a seasoned collector, we have the perfect Koi for you.
            </Paragraph>
            <Paragraph>
              Contact us today to learn more about our Koi fish and how they can
              transform your water garden into a serene sanctuary.
            </Paragraph>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default AboutUs;
