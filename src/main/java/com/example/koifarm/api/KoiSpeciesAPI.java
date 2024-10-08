package com.example.koifarm.api;

import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.model.KoiSpeciesRequest;
import com.example.koifarm.service.KoiSpeciesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/koi-species")
@CrossOrigin("*") // Allows CORS requests
@SecurityRequirement(name = "api")
public class KoiSpeciesAPI {

    @Autowired
    KoiSpeciesService koiSpeciesService;

//    Authorization for STAFF role to create a new species
    @PreAuthorize("hasAuthority('STAFF')")
    @PostMapping("")
    public ResponseEntity<KoiSpecies> create( @RequestBody KoiSpeciesRequest koiSpeciesRequest) {
        KoiSpecies koiSpecies = koiSpeciesService.createKoiSpecies(koiSpeciesRequest);
        return ResponseEntity.ok(koiSpecies);
    }
}
