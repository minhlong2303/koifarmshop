package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    float price;
    int quantity;

    @ManyToOne
            @JoinColumn(name = "order_id")
            @JsonIgnore
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "koi_id")
            @JsonIgnore
    Koi koi;
}
