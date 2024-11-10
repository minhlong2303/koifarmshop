/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import "./HomePage.css";

import KoiList from "../../components/KoiList";
import api from "../../config/axios";
import { useDispatch } from "react-redux";
import { addProduct } from "../../redux/features/cartSlice";
import { Divider } from "antd";


function HomePage() {
  const [category, setCategory] = useState("All");
  const [products, setProducts] = useState([]);
  const fetchProduct = async () => {
    try {
      const response = await api.get("/koi");

      setProducts(response.data);
    } catch (e) {
      console.log("Error fetch product: ", e);
    }
  };

  useEffect(() => {
    fetchProduct();
  }, []);
  return (
    <>
      <div className="header">
        <img
          src="src/assets/z5887354582155_4aaf9324965efeb7a25ee98ce5cef5f2.jpg"
          alt=""
        />
      </div>
      <div>
        <KoiList category={category} setCategory={setCategory} />
        <Divider style={{ borderColor: "gray" }} />
        <h2>KOI ĐANG BÁN</h2>
        <div className="product-list">
          {products.map((product) => (
            <Product key={product.id} product={product} />
          ))}
        </div>
      </div>
      <Divider style={{ borderColor: "gray" }} />
      <h2>CÁC LOÀI CÁ KOI TIÊU BIỂU</h2>

      {/* Koi Types Information */}
      <KoiTypeInfo />
    </>
  );
}
const Product = ({ product }) => {
  const dispatch = useDispatch();

  const handleAddToCart = () => {
    dispatch(addProduct(product));
  };
  return (
    <>
      <div className="product">
        <img src={product.image} alt="" />
        <h3>{product.name}</h3>
        <p>{product.description}</p>
        <span>{`${product.price} vnđ`}</span>
        <center>
          <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
        </center>
      </div>
    </>
  );
};

const KoiTypeInfo = () => (
  <>
    {/* Koi Kohaku */}
    <div className="koi-box koi-kohaku">
      <div className="koi-box-header">KOI KOHAKU</div>
      <img
          src="src/assets/kohakuu.jpg"
          alt=""
        />
      <div className="koi-box-description">
        <p>Cá Koi Kohaku thân đỏ trắng là dòng cá tiêu biểu, khả năng sinh sôi nảy nở cực tốt và cũng là 1 trong những dòng cá có tuổi thọ cao.
          Vì thế còn được gọi là “Cá Trường Thọ”. Thời xa xưa ở các nước Á Đông người ta thường quan niệm nhà nào đông con nhiều cháu là nhà
          đó có nhiều phúc đức, bởi thế có 1 số gia đình đến thời nay vẫn giữ 1 thói quen là đêm động phòng ngoài dán chữ Hỷ trong phòng họ
          còn bày biện thêm 1 món quà làm vật trang trí là 1 đôi cá chép thân đỏ trắng tượng trưng cho sự sinh sản nhằm cầu chúc cho cặp đôi
          luôn được sống hạnh phúc, con đàn cháu đống, nhân đinh hưng vượng.</p>
          
      </div>
    </div>

    {/* Koi Tancho */}
    <div className="koi-box koi-tancho">
      <div className="koi-box-header">KOI TANCHO</div>
      <img
          src="src/assets/tanchoo.jpg"
          alt=""
        />
      <div className="koi-box-description">
        <p>Tancho là giống cá koi có chấm tròn màu đỏ nằm chính giữa trung tâm phần đầu của chúng. Ba loại cá cơ bản của Tancho là Tancho-kohaku,
          Tancho-Sanke, Tancho-Showa đều có hình dáng bên ngoài giống như cá chép koi chuẩn. Tuy nhiên loại cá này có vảy đục, vây đẹp nhìn rõ
          các tia vây, màu sắc rực rỡ, mắt to, râu dài và thân mình tròn trịa, đầu hơi gù.<br/><br/>
          Cá Koi Tancho Kohaku là loài cá được các bậc quan chức nhân sĩ ưa chuộng và được mệnh danh là loài cá chép có “cốt cách nhà quan”.
          Mình cá màu trắng tuyết rất đẹp, đầu cá có màu đỏ nổi bật, giống biểu tượng của cờ nước Nhật – tượng trưng cho đất nước mặt trời đỏ, tượng trưng
          cho nước nhà nên quan lại rất thích chọn lựa nuôi dưỡng.</p>
          
      </div>
    </div>

    {/* Koi Ogon */}
    <div className="koi-box koi-ogon">
      <div className="koi-box-header">KOI OGON</div>
      <img
          src="src/assets/ogonn.png"
          alt=""
        />
      <div className="koi-box-description">
        <p>Cá Koi Ogon là một biểu tượng của sự đơn giản và sự lấp lánh trong thế giới của cá cảnh. Mặc dù không có các hoa văn phức tạp như các
          loại cá Koi khác, nhưng Ogon vẫn thu hút ánh nhìn bởi vẻ đẹp rực rỡ và sự sang trọng của mình. Tại Việt Nam, Ogon là một trong những
          loại cá Koi được người chơi cá cảnh yêu thích và chú ý đặc biệt.<br/><br/>
          Đặc điểm chính của Ogon là màu vàng hoặc cam rực rỡ phủ trên toàn bộ cơ thể. Sự đơn giản và tinh tế của màu sắc này tạo ra một hiệu ứng lấp lánh
          rực rỡ khi Ogon bơi trong ao. Ánh nắng mặt trời chiếu xuống làm cho màu sắc của Ogon trở nên lung linh và lôi cuốn, tạo ra một cảm giác bình yên và yên bình.</p>
      </div>
    </div>

    {/* Koi Sanke */}
    <div className="koi-box koi-sanke">
      <div className="koi-box-header">KOI SANKE</div>
      <img
          src="src/assets/sankee.png"
          alt=""
        />
      <div className="koi-box-description">
        <p>Cá koi Sanke với màu trắng trong trẻo của thân cá, kết hợp với các vệt màu đỏ và đen tạo thành các hoa văn phức tạp. Mỗi một vệt màu đỏ và đen
          trên thân cá Sanke đều được tạo ra một cách tỉ mỉ và cân nhắc, tạo ra một bức tranh sống động và lôi cuốn khi Sanke bơi trong nước. Sự phối hợp
          giữa ba màu sắc này tạo nên một vẻ đẹp tinh tế và độc đáo, thu hút ánh nhìn ngay từ cái nhìn đầu tiên.<br/><br/>
          Ngoài vẻ đẹp ngoại hình, cá koi Sanke còn mang theo một ý nghĩa sâu sắc trong văn hóa Nhật Bản. Màu trắng của thân cá thường được liên kết với sự trong sáng
          và thanh khiết, trong khi màu đỏ biểu hiện sức mạnh và sự may mắn, và màu đen thể hiện sự kiên nhẫn và sự cứng cỏi. Do đó, Sanke thường được coi là biểu tượng
          của sự cân bằng và may mắn trong tư duy phong thủy.</p>
      </div>
    </div>
  </>
);



export default HomePage;
