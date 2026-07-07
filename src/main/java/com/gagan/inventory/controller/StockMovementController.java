package com.gagan.inventory.controller;

import com.gagan.inventory.dto.response.PageResponse;
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
    public PageResponse<StockMovementResponse> getAllStockMovements(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                    @RequestParam(defaultValue = "desc") String direction) {
        return stockMovementService
                .getAllStockMovements(page, size, sortBy, direction);
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