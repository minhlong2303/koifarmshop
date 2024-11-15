import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: [],
  reducers: {
    addProduct: (state, action) => {
      const { koiID, batchKoiID, quantity = 1 } = action.payload;

      const existingProduct = state.find(
        (item) =>
          (item.koiID && item.koiID === koiID) ||
          (item.batchKoiID && item.batchKoiID === batchKoiID)
      );

      if (existingProduct) {
        // Increment the quantity by the specified amount if the item exists
        existingProduct.quantity += quantity;
      } else {
        // Add a new product with the specified quantity
        state.push({ ...action.payload, quantity });
      }
    },
    clearAll: () => {
      return [];
    },
    removeSelected: (state, action) => {
      return state.filter(
        (item) =>
          !action.payload.includes(item.koiID) &&
          !action.payload.includes(item.batchKoiID)
      );
    },
    loadCart: (state, action) => {
      return action.payload;
    },
    // Thêm action để xóa từng sản phẩm
    removeFromCart: (state, action) => {
      const { koiID, batchKoiID } = action.payload;
      return state.filter(
        (item) => item.koiID !== koiID && item.batchKoiID !== batchKoiID
      );
    },
  },
});

export const {
  addProduct,
  clearAll,
  removeSelected,
  loadCart,
  removeFromCart,
} = cartSlice.actions;
export default cartSlice.reducer;
