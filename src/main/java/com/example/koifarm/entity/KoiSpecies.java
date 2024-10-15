package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

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
    Set<Koi> kois;

    // Getters and Setters
}

