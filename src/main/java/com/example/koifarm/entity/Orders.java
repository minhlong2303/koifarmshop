package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    Date date;

    float total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;

    @OneToOne(mappedBy = "orders")
    Payment payment;
}