package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.ProductAddDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        return productService.addProduct(productAddDto);
    }

    @GetMapping()
    public List<Product> showAllProducts() {
        return productService.showAllProducts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
