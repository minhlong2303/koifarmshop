package com.example.koifarm.api;

import com.example.koifarm.entity.BatchKoi;
import com.example.koifarm.model.BatchKoiRequest;
import com.example.koifarm.service.BatchKoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/batchKoi")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class BatchKoiAPI {
    @Autowired
    BatchKoiService batchKoiService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BatchKoiRequest batchKoiRequest){
        BatchKoi newBatchKoi = batchKoiService.create(batchKoiRequest);
        return ResponseEntity.ok(newBatchKoi);
    }

    @GetMapping
    public ResponseEntity get(){
        List<BatchKoi> batchKoi = batchKoiService.getAllBatchKoi();
        return ResponseEntity.ok(batchKoi);
    }

    //lay thong tin chi tiet cua 1 lo Koi cu the
    @GetMapping("{batchKoiID}")
    public ResponseEntity getbatchKoiDetails(@PathVariable UUID batchKoiID){
        BatchKoi batchKoi = batchKoiService.getBatchKoiById(batchKoiID);
        return ResponseEntity.ok(batchKoi);
    }

    @PutMapping("{batchKoiID}")
    public ResponseEntity update(@PathVariable UUID batchKoiID, @Valid @RequestBody BatchKoi batchKoi){
        BatchKoi newBatchKoi = batchKoiService.update(batchKoiID, batchKoi);
        return ResponseEntity.ok(newBatchKoi);
    }

    @DeleteMapping("{batchKoiID}")
    public ResponseEntity delete(@PathVariable UUID batchKoiID){
        BatchKoi deletedBatchKoi = batchKoiService.delete(batchKoiID);
        return ResponseEntity.ok(deletedBatchKoi);
    }

}