package com.mrokos.RelexProject.repositories;

import com.mrokos.RelexProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByItemName (String itemName);
}
