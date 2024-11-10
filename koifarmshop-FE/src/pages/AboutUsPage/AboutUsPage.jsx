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
            <Title level={2}>Về chúng tôi</Title>
            <Paragraph>
              Chào mừng đến với doanh nghiệp Cá Koi của chúng tôi! Chúng tôi chuyên về
              nhân giống và bán cá Koi cao cấp, cung cấp cho khách hàng những loại cá
              chất lượng cao nhất cho ao của họ. Đội ngũ chuyên gia của chúng tôi đảm
              bảo rằng mỗi con cá Koi đều khỏe mạnh, tươi tắn và được nuôi trong môi
              trường nguyên sơ.
            </Paragraph>
            <Paragraph>
              Sứ mệnh của chúng tôi là mang lại sự yên tĩnh và vẻ đẹp cho ao của bạn
              với bộ sưu tập cá Koi tuyệt đẹp của chúng tôi. Cho dù bạn là người mới
              bắt đầu hay là người sưu tập dày dạn kinh nghiệm, chúng tôi đều có
              những chú cá Koi hoàn hảo dành cho bạn.

            </Paragraph>
            <Paragraph>
              Hãy liên hệ với chúng tôi ngay hôm nay để tìm hiểu thêm về cá Koi của
              chúng tôi và cách chúng có thể biến khu vườn nước của bạn thành nơi
              ẩn náu thanh bình.
            </Paragraph>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default AboutUs;
