package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.services.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update")
public class StatController {
    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateDto productUpdateDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(statService.updateProduct(productUpdateDto, token));
    }
}
