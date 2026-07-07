package com.gagan.inventory.controller;

import com.gagan.inventory.dto.response.StockMovementResponse;
import com.gagan.inventory.service.StockMovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-movements")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    public List<StockMovementResponse> getAllStockMovements() {
        return stockMovementService
                .getAllStockMovements();
    }

    @GetMapping("/products/{productId}")
    public List<StockMovementResponse> getStockMovementsByProduct(
            @PathVariable Long productId) {

        return stockMovementService
                .getStockMovementsByProduct(
                        productId
                );
    }

    @GetMapping("/warehouses/{warehouseId}")
    public List<StockMovementResponse> getStockMovementsByWarehouse(
            @PathVariable Long warehouseId) {

        return stockMovementService
                .getStockMovementsByWarehouse(
                        warehouseId
                );
    }
}