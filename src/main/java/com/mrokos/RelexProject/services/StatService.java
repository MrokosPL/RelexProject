package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.ProdUpdateResponseDto;
import com.mrokos.RelexProject.dtos.ProductUpdateDto;
import com.mrokos.RelexProject.entities.Product;
import com.mrokos.RelexProject.entities.Statistic;
import com.mrokos.RelexProject.repositories.ProductRepository;
import com.mrokos.RelexProject.repositories.StatRepository;
import com.mrokos.RelexProject.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service

public class StatService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final StatRepository statRepository;
    private final ProductRepository productRepository;

    public StatService(TokenUtils tokenUtils, UserService userService, StatRepository statRepository, ProductRepository productRepository) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.statRepository = statRepository;
        this.productRepository = productRepository;
    }

    public ProdUpdateResponseDto updateProduct(@RequestBody ProductUpdateDto productUpdateDto, String header) {
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
