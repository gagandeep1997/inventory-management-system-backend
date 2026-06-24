package com.gagan.inventory.repository;

import com.gagan.inventory.entity.TransferItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferItemRepository extends JpaRepository<TransferItem, Long> {
    
}