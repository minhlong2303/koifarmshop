import { useRef } from "react";
import "./index.css";
import { AiOutlineLeftCircle, AiOutlineRightCircle } from "react-icons/ai";
import { koi_list } from "../../assets/assets";
import { Button } from "antd";

function KoiList() {
  const scrollRef = useRef(null);

  // Function to scroll left
  const scrollLeft = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollLeft -= 150; // Adjust the value to scroll more or less
    }
  };

  // Function to scroll right
  const scrollRight = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollLeft += 150; // Adjust the value to scroll more or less
    }
  };

  return (
    <div className="koi_list">
      <Button id="btnLeft" onClick={scrollLeft}>
        <AiOutlineLeftCircle size={25} />
      </Button>
      <div className="koi_scroll" ref={scrollRef}>
        {koi_list.map((item, index) => {
          return (
            <div key={index} className="koi_menu_items">
              <img src={item.koi_image} alt="" />
              <p>{item.koi_name}</p>
            </div>
          );
        })}
      </div>
      <Button id="btnRight" onClick={scrollRight}>
        <AiOutlineRightCircle size={25} />
      </Button>
    </div>
  );
}

export default KoiList;
