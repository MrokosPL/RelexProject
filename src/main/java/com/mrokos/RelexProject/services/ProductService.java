package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.ProdUpdateResponseDto;
import com.mrokos.RelexProject.dtos.ProductAddDto;
import com.mrokos.RelexProject.dtos.ProductResponseDto;
import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.entities.Statistic;
import com.mrokos.RelexProject.exceptions.AppExeption;
import com.mrokos.RelexProject.repositories.ProductRepository;
import com.mrokos.RelexProject.repositories.StatRepository;
import com.mrokos.RelexProject.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final StatRepository statRepository;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(StatRepository statRepository, TokenUtils tokenUtils, UserService userService, ProductRepository productRepository) {
        this.statRepository = statRepository;
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        if (productRepository.existsByItemName(productAddDto.getItemName())) {
            return new ResponseEntity<>(new AppExeption(HttpStatus.BAD_REQUEST.value(), "Такой товар уже существует"), HttpStatus.BAD_REQUEST);
        }
        Product product = new Product();
        product.setItemname(productAddDto.getItemName());
        product.setQuantity(productAddDto.getQuantity());
        product.setMeasurement(productAddDto.getMeasurement());
        product.setCreatedAt(LocalDate.now());
        product.setChangedAt(LocalDate.now());
        productRepository.save(product);
        return ResponseEntity.ok(new ProductResponseDto(product.getId(), product.getItemname(), product.getQuantity(), product.getMeasurement()));
    }

    public List<Product> showAllProducts() {
        return productRepository.findAll(Sort.by("itemName"));
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(new AppExeption(HttpStatus.NOT_FOUND.value(), "Такого товара не существует"), HttpStatus.BAD_REQUEST);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Продукт удалён");
    }

    public ProdUpdateResponseDto updateProduct(ProductUpdateDto productUpdateDto, String header) {
        Product product = productRepository.findByItemName(productUpdateDto.getItemName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар с таким именем не существует"));
        product.setQuantity(product.getQuantity() + productUpdateDto.getQuantity());
        product.setChangedAt(LocalDate.now());
        productRepository.save(product);
        Statistic statistic = new Statistic();
        String token = header.substring(7);
        statistic.setUser(userService.findByEmail(tokenUtils.getJwtMail(token)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого пользователя не существует")));
        statistic.setProduct(product);
        statistic.setProducedQuantity(productUpdateDto.getQuantity());
        statistic.setCreatedAt(LocalDate.now());
        statRepository.save(statistic);
        return new ProdUpdateResponseDto(tokenUtils.getJwtMail(token), productUpdateDto.getItemName(), productUpdateDto.getQuantity(), statistic.getCreatedAt());
    }
}
