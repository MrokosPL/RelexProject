package com.mrokos.RelexProject.repositories;

import com.mrokos.RelexProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByItemName(String itemName);

    Optional<Product> findByItemName(String itemName);
}
