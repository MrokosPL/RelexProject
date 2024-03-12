package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.ProductAddDto;
import com.mrokos.RelexProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;
@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct (@RequestBody ProductAddDto productAddDto){
        return productService.addProduct(productAddDto);
    }

}
