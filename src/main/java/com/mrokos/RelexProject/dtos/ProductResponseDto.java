package com.mrokos.RelexProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String itemName;
    private Double quantity;
}
