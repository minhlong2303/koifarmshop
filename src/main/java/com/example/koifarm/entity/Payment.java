package com.example.koifarm.entity;

import com.example.koifarm.enums.PaymentEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Creation date is required")
    LocalDateTime createAt;

    @NotNull(message = "Total is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than zero")
    float total;

    @Enumerated(EnumType.STRING)
    PaymentEnums payment_method;

    @OneToOne(cascade = CascadeType.PERSIST)  // Adjust cascade as needed
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
