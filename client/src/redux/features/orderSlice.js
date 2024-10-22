// orderSlice.js
import { createSlice } from "@reduxjs/toolkit";

const orderSlice = createSlice({
  name: "order",
  initialState: [],
  reducers: {
    createOrder: (state, action) => {
      return action.payload; // Replace the existing state with the new selected items
    },
    clearOrder: () => {
      return []; // Clear the order state
    },
    cancelOrder: (state, action) => {
      const orderId = action.payload;
      state.orders = state.orders.filter((order) => order.id !== orderId);
    },
    setOrderStatus: (state, action) => {
      state.status = action.payload;
    },
    setError: (state, action) => {
      state.error = action.payload;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
});

export const {
  createOrder,
  clearOrder,
  cancelOrder,
  setOrderStatus,
  setError,
  clearError,
} = orderSlice.actions;

export default orderSlice.reducer;
