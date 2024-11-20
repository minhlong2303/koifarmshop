package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull(message = "Price is required")
    float price;

    @NotNull(message = "Quantity is required")
    int quantity;

    @NotNull(message = "Item type is required")
    private String itemType;

    String status = "available"; // Default status

    @Transient
    private UUID koiId;

    // Getter cho koiId để hiển thị
    @JsonProperty("koiId")
    public UUID getKoiId() {
        return koi != null ? koi.getKoiID() : null;
    }

    @Transient
    private UUID batchKoiId;

    // Getter cho batchKoiId để hiển thị
    @JsonProperty("batchKoiId")
    public UUID getBatchKoiId() {
        return batchKoi != null ? batchKoi.getBatchKoiID() : null;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Orders orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "koi_id")
    Koi koi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batchkoi_id")
    BatchKoi batchKoi;
}
