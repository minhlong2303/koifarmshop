import { combineReducers } from "@reduxjs/toolkit";
import counterReducer from "../redux/features/counterSlice";
import userReducer from "../redux/features/userSlice";
import cartReducer from "../redux/features/cartSlice";

export const rootReducer = combineReducers({
  counter: counterReducer,
  user: userReducer,
  cart: cartReducer,
});
