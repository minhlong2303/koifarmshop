package com.example.koifarm.api;

import com.example.koifarm.entity.Koi;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.service.KoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/koi")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class KoiAPI {

    @Autowired
    KoiService koiService;

    @PreAuthorize("hasAuthority('MANAGER')")
    //@PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER')")


    //them Koi moi
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody KoiRequest koi){
        Koi newKoi = koiService.create(koi);
        return ResponseEntity.ok(newKoi);
    }

    //get danh sach koi
    @GetMapping
    public ResponseEntity get(){
        List<Koi> kois = koiService.getAllKoi();
        return ResponseEntity.ok(kois);
    }

    // Get details of a specific Koi by ID
    @GetMapping("{koiID}")
    public ResponseEntity getKoiDetails(@PathVariable UUID koiID) {
        Koi koi = koiService.getKoiById(koiID);
        return ResponseEntity.ok(koi);
    }

    //api/koi/{koiId}
    //update Koi
    @PutMapping("{koiID}")
    public ResponseEntity update(@PathVariable UUID koiID, @Valid @RequestBody Koi koi ){
        Koi newKoi = koiService.update(koiID, koi);
        return ResponseEntity.ok(newKoi);
    }

    //delete Koi
    @DeleteMapping("{koiID}")
    public ResponseEntity delete(@PathVariable UUID koiID){
        Koi deletedKoi = koiService.delete(koiID);
        return ResponseEntity.ok(deletedKoi);
    }
}
