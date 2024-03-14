package com.mrokos.RelexProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProdUpdateResponseDto {
    private String email;
    private String itemName;
    private Double quantity;
    private LocalDate createdAt;
}
