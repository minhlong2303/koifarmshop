package com.example.koifarm.model;

import com.example.koifarm.entity.User;
import com.example.koifarm.entity.Koi;

public class ConsignmentRequest {
    private Koi koi;
    private User seller;

    // Getters và Setters
    public Koi getKoi() {
        return koi;
    }

    public void setKoi(Koi koi) {
        this.koi = koi;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
