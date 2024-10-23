package com.example.koifarm.entity;

import com.example.koifarm.enums.PaymentEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Date createAt;

    float total;

    @Enumerated(EnumType.STRING)
    PaymentEnums payment_method;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
            @JsonIgnore
    Orders orders;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    Set<Transactions> transactions;


}
