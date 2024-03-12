package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.ProductAddDto;
import com.mrokos.RelexProject.dtos.ProductResponseDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.exceptions.AppExeption;
import com.mrokos.RelexProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto){
        if(productRepository.findByItemName(productAddDto.getItemName())!= null){
            return new ResponseEntity<>(new AppExeption(HttpStatus.BAD_REQUEST.value(), "Такой товар уже существует"), HttpStatus.BAD_REQUEST);
        }
        Product product = new Product();
        product.setItemname(productAddDto.getItemName());
        product.setQuantity(productAddDto.getQuantity());
        product.setCreatedAt(LocalDateTime.now());
        product.setChangedAt(LocalDateTime.now());
        productRepository.save(product);
        return ResponseEntity.ok(new ProductResponseDto(product.getId(), product.getItemname(), product.getQuantity()));
    }
}
