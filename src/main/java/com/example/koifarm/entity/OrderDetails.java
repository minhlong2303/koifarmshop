package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // Getter cho koiId
    public UUID getKoiId() {
        // Nếu koi không phải null, lấy koiId từ đối tượng koi
        return koi != null ? koi.getKoiID() : null;
    }

    @ManyToOne
            @JoinColumn(name = "order_id")
            @JsonIgnore
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "koi_id")
            @JsonIgnore
    Koi koi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batchkoi_id")
    @JsonIgnore
    BatchKoi batchKoi;
}
