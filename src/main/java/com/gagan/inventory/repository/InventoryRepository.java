package com.gagan.inventory.repository;

import com.gagan.inventory.entity.Inventory;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);
}