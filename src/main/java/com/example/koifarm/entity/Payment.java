
package com.example.koifarm.entity;

import com.example.koifarm.enums.PaymentEnums;
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

    @Enumerated(EnumType.STRING)
    PaymentEnums payment_method;

    @OneToOne
    @JoinColumn(name = "order_id")
    Orders orders;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    Set<Transactions> transactions;
}
