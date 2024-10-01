package com.example.koifarm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private Long userId; // Mỗi giỏ hàng sẽ thuộc về một người dùng

    // Constructor mặc định
    public Cart() {
    }

    // Constructor đầy đủ
    public Cart(Long userId) {
        this.userId = userId;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Thêm phương thức để thêm sản phẩm vào giỏ
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this); // Đảm bảo rằng CartItem liên kết ngược lại với Cart
    }

    // Phương thức để xóa một sản phẩm từ giỏ
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null); // Bỏ liên kết ngược lại để không còn thuộc về giỏ
    }

    // Phương thức để tính tổng giá trị của giỏ hàng
    public BigDecimal calculateCartTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.calculateTotalPrice()); // Call calculateTotalPrice
        }
        return total; // Return the total price of all items in the cart
    }
}
