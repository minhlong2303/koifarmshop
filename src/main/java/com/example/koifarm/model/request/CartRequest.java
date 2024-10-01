package com.example.koifarm.model.request;

public class CartRequest {
    Long koiId; // ID của Koi cần thêm vào giỏ
    int quantity; // Số lượng Koi muốn mua

    // Getter và Setter cho koiId
    public Long getKoiId() {
        return koiId;
    }

    public void setKoiId(Long koiId) {
        this.koiId = koiId;
    }

    // Getter và Setter cho quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
