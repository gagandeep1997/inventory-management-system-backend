package com.gagan.inventory.repository;

import com.gagan.inventory.entity.Product;
import com.gagan.inventory.entity.StockMovement;
import com.gagan.inventory.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProduct(Product product);

    List<StockMovement> findByWarehouse(Warehouse warehouse);
}