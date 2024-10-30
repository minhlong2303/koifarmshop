package com.example.koifarm.api;

import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.model.KoiSpeciesRequest;
import com.example.koifarm.service.KoiSpeciesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/koi-species")
@CrossOrigin("*") // Allows CORS requests
@SecurityRequirement(name = "api")
public class KoiSpeciesAPI {

    @Autowired
    KoiSpeciesService koiSpeciesService;

    //Authorization for STAFF role to create a new species
    //@PreAuthorize("hasAuthority('STAFF')")
    @PostMapping
    public ResponseEntity<KoiSpecies> create( @RequestBody KoiSpeciesRequest koiSpeciesRequest) {
        KoiSpecies koiSpecies = koiSpeciesService.createKoiSpecies(koiSpeciesRequest);
        return ResponseEntity.ok(koiSpecies);
    }

    @GetMapping
    public ResponseEntity getAllKoiSpecies(){
        List<KoiSpecies> koiSpeciesList = koiSpeciesService.getAllKoiSpecies();
        return ResponseEntity.ok(koiSpeciesList);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @Valid @RequestBody KoiSpecies koiSpecies){
        KoiSpecies newKoiSpecies = koiSpeciesService.update(id, koiSpecies);
        return ResponseEntity.ok(newKoiSpecies);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        KoiSpecies delKoiSpecies = koiSpeciesService.delete(id);
        return ResponseEntity.ok(delKoiSpecies);
    }



}
