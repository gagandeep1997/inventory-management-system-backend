package com.gagan.inventory.repository;

import com.gagan.inventory.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(
            @NotBlank(message = "SKU is required")
            @Size(max = 50, message = "SKU must not exceed 50 characters")
            String sku
    );
}