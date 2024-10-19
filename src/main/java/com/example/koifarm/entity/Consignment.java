package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Temporal(TemporalType.TIMESTAMP)
    Date consignmentDate;

    float total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL)
    List<ConsignmentDetails> consignmentDetails;
}
