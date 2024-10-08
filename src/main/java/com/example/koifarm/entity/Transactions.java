package com.example.koifarm.entity;

import com.example.koifarm.enums.TransactionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "from_id")
    User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    User to;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

    TransactionEnum status;

    String description;
}
