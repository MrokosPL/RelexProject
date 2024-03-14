package com.mrokos.RelexProject.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double quantity;
    private String measurement;
    private LocalDate dateCreated;
    private LocalDate dateChanged;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemname() {
        return itemName;
    }

    public void setItemname(String itemname) {
        this.itemName = itemname;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.dateCreated = createdAt;
    }

    public LocalDate getChangedAt() {
        return dateChanged;
    }

    public void setChangedAt(LocalDate changedAt) {
        this.dateChanged = changedAt;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
