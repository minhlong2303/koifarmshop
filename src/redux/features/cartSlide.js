import { createSlice } from "@reduxjs/toolkit";


const cartSlide = createSlice({
    name: "cart",
    initialState: [],
    reducers: {
    addProduct: (state, action) => {
    const product = action.payload;

            //check con ca co trong mang redux chua?
    const existProduct = state.find((koi) => koi.id === product.id)
            //co thi tang quantity
    if(existProduct) {
    existProduct.quantity += 1;
}
            //khong thi add vao va them fiel quantity

else {
    state.push({...product, quantity: 1});
}
            
           
},
clearAll: () => [],
    },
});

export const {addProduct, clearAll} = cartSlide.actions;
export default cartSlide.reducer;