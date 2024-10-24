import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: [],
  reducers: {
    addProduct: (state, action) => {
      const product = action.payload;
      // Kiểm tra xem cá có trong mảng redux chưa
      const existKoi = state.find((koi) => koi.koiID === product.koiID);
      // Nếu có thì tăng quantity lên
      if (existKoi) {
        existKoi.quantity += 1;
      }
      // Nếu không thì push vào và thêm field quantity cho nó
      else {
        state.push({ ...product, quantity: 1 });
      }
    },
    clearAll: () => {
      return [];
    },
    removeSelected: (state, action) => {
      const idsToRemove = action.payload;
      return state.filter((koi) => !idsToRemove.includes(koi.koiID)); // Lọc ra những sản phẩm không nằm trong mảng idsToRemove
    },
  },
});

export const { addProduct, clearAll, removeSelected } = cartSlice.actions;
export default cartSlice.reducer;
