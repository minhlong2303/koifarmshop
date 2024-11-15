package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Price is required")
    float price;

    @NotNull(message = "Quantity is required")
    int quantity;

    @NotNull(message = "Item type is required")
    private String itemType;

    String status = "available"; // Default status

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Orders orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "koi_id")
    @JsonIgnore
    Koi koi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batchkoi_id")
    @JsonIgnore
    BatchKoi batchKoi;
}
