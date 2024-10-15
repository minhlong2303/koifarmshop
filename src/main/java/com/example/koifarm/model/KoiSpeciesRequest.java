package com.example.koifarm.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KoiSpeciesRequest {
    long id;
    String name;
    String description;
}
