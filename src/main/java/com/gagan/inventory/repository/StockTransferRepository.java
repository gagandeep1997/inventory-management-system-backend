package com.gagan.inventory.repository;

import com.gagan.inventory.entity.StockTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransferRepository extends JpaRepository<StockTransfer, Long> {
    
}