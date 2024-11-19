package com.example.koifarm.entity;

import com.example.koifarm.enums.TransactionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_id")
            @JsonIgnore
    User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    @JsonIgnore
    User to;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnore
    Payment payment;

    @Enumerated(EnumType.STRING)
    TransactionEnum status;

    Date createAt;
    String description;

    float amount;
}
