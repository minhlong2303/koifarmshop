import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: [],
  reducers: {
    addProduct: (state, action) => {
      const product = action.payload;
      //Kiem tra coi con ca có trong mảng redux chưa
      const existKoi = state.find((koi) => koi.koiID === product.koiID);
      //Nếu có thì tăng quantity lên
      if (existKoi) {
        existKoi.quantity = existKoi.quantity + 1;
      }
      //Nếu không thì push vào và thêm field quantity cho nó
      else {
        state.push({ ...product, quantity: 1 });
      }
    },
    clearAll: () => {
      return [];
    },
  },
});

export const { addProduct, clearAll } = cartSlice.actions;
export default cartSlice.reducer;
