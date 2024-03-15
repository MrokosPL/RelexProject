package com.mrokos.RelexProject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double quantity;
    private String measurement;
    private LocalDate dateCreated;
    private LocalDate dateChanged;

}
