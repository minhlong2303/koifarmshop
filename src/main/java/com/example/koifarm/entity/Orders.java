package com.example.koifarm.entity;

import com.example.koifarm.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    LocalDateTime date;

    float total;

    @Enumerated(EnumType.STRING)
    OrderStatusEnum status;

    @JsonIgnore
    boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    Payment payment;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<Feedback> feedbacks;

    // Initialize date on entity creation
    @PrePersist
    public void onPrePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    @ElementCollection
    private List<String> koiId = new ArrayList<>();

    @ElementCollection
    private List<String> batchKoiId = new ArrayList<>();


    public List<String> getKoiId() {
        return koiId;
    }

    public void setKoiId(List<String> koiId) {
        this.koiId = koiId;
    }

    public List<String> getBatchKoiId() {
        return batchKoiId;
    }

    public void setBatchKoiId(List<String> batchKoiId) {
        this.batchKoiId = batchKoiId;
    }
}






