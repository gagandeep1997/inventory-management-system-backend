package com.gagan.inventory.service;

import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.StockMovementResponse;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.entity.StockMovementType;
import com.gagan.inventory.entity.Warehouse;

import java.util.List;

public interface StockMovementService {
    void recordMovement(
            Product product,
            Warehouse warehouse,
            StockMovementType movementType,
            Integer quantity
    );
    PageResponse<StockMovementResponse> getAllStockMovements(int page, int size, String sortBy, String direction);
    List<StockMovementResponse> getStockMovementsByProduct(Long productId);
    List<StockMovementResponse> getStockMovementsByWarehouse(Long warehouseId);
}
