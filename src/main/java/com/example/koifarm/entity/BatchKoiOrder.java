package com.example.koifarm.entity;

import com.example.koifarm.enums.OrderStatusEnum;
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
public class BatchKoiOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date date;

    private float total;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private User customer;

    @OneToMany(mappedBy = "batchKoiOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchKoiOrderDetail> batchOrderDetails;

    @OneToOne(mappedBy = "batchKoiOrder", cascade = CascadeType.ALL)
    private Payment payment;

}
