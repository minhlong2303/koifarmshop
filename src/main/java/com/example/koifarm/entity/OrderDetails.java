package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

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
