package com.example.koifarm.api;

import com.example.koifarm.model.BatchKoiResponse;
import com.example.koifarm.model.KoiResponse;
import com.example.koifarm.service.KoiPageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class PageKoiAPI {
    @Autowired
    KoiPageService koiPageService;

    @GetMapping("pageKoi")
    public ResponseEntity get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue="5") int size){
        KoiResponse koiResponse = koiPageService.getPageKoi(page, size);
        return ResponseEntity.ok(koiResponse);
    }

    @GetMapping("pageBatchKoi")
    public ResponseEntity getBatchKoiPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        BatchKoiResponse batchKoiResponse = koiPageService.get(page, size);
        return ResponseEntity.ok(batchKoiResponse);
    }
}
