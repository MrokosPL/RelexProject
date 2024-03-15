package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.ProductAddDto;
import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        return productService.addProduct(productAddDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public List<Product> showAllProducts() {
        return productService.showAllProducts();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
    @PutMapping
    @PreAuthorize("Authenticated")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateDto productUpdateDto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(productService.updateProduct(productUpdateDto, token));
    }

}
