package com.example.koifarm.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KoiSpeciesRequest {
    String name;
    String description;
}
