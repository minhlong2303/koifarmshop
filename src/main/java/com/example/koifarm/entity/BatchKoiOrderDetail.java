package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BatchKoiOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    float price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "batch_order_id")
    @JsonIgnore
    private BatchKoiOrder batchKoiOrder;

    @ManyToOne
    @JoinColumn(name = "batch_koi_id")
    @JsonIgnore
    private BatchKoi batchKoi;
}
