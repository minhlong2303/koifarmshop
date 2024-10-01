package com.example.koifarm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "koi_id", nullable = false)
    private Koi koi;

    private int quantity;

    // Default constructor
    public CartItem() {
    }

    // Full constructor
    public CartItem(Cart cart, Koi koi, int quantity) {
        this.cart = cart;
        this.koi = koi;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Koi getKoi() {
        return koi;
    }

    public void setKoi(Koi koi) {
        this.koi = koi;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to calculate the total price of this CartItem
    public BigDecimal calculateTotalPrice() {
        if (koi != null && koi.getPrice() != null) {
            return koi.getPrice().multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO; // Return zero if koi or price is not available
    }
}
