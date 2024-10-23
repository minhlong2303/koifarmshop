package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private int rating;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    User customer;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "owner_id")
//    User owner;

}
