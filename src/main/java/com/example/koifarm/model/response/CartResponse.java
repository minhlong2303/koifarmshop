package com.example.koifarm.model.response;

import com.example.koifarm.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {

    Long cartId;
    List<CartItem> items;
    int totalQuantity;
    BigDecimal totalPrice; // Kiểu BigDecimal

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
