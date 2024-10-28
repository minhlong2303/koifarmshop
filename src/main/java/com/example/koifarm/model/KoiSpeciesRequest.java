package com.example.koifarm.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class KoiSpeciesRequest {
    long id;
    String name;
    String description;
}
