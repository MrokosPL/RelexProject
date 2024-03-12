package com.mrokos.RelexProject.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double quantity;
    private LocalDateTime dateCreated;
    private LocalDateTime dateChanged;

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.dateCreated = createdAt;
    }

    public LocalDateTime getChangedAt() {
        return dateChanged;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.dateChanged = changedAt;
    }
}
