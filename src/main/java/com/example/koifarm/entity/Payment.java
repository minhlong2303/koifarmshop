package com.example.koifarm.entity;

import com.example.koifarm.enums.PaymentEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    LocalDateTime createAt;
    float total;

    @Enumerated(EnumType.STRING)
    PaymentEnums payment_method;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Orders orders;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    Set<Transactions> transactions;

    @PrePersist
    public void onPrePersist() {
        if (this.createAt == null) {
            this.createAt = LocalDateTime.now();
        }
    }
}