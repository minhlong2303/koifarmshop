package com.example.koifarm.entity;

import com.example.koifarm.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull(message = "Date is required")
    LocalDateTime date;

    @NotNull(message = "Total is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than zero")
    float total;

    @Enumerated(EnumType.STRING)
    OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;

    @OneToOne(mappedBy = "orders")
    Payment payment;

    // Initialize date on entity creation
    @PrePersist
    public void onPrePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }
}
