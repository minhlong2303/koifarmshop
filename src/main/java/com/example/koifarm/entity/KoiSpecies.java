package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
//@Table(name = "KoiSpecies")
public class KoiSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonIgnore
    boolean isDeleted = false;

    String name;
    String description;

    // One-to-Many relationship with Koi
    @OneToMany(mappedBy = "species")
    List<Koi> kois;

    // One-to-Many relationship with BatchKoi
    @OneToMany(mappedBy = "species")
     List<BatchKoi> batchKois;
}
