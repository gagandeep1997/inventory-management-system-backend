package com.gagan.inventory.controller;

import com.gagan.inventory.dto.request.AddStockRequest;
import com.gagan.inventory.dto.request.AdjustStockRequest;
import com.gagan.inventory.dto.request.RemoveStockRequest;
import com.gagan.inventory.dto.response.InventoryResponse;
import com.gagan.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add-stock")
    public InventoryResponse addStock(@Valid @RequestBody AddStockRequest request) {
        return inventoryService.addStock(request);
    }

    @PostMapping("/remove-stock")
    public InventoryResponse removeStock(@Valid @RequestBody RemoveStockRequest request) {
        return inventoryService.removeStock(request);
    }

    @PostMapping("/adjust-stock")
    public InventoryResponse adjustStock(@Valid @RequestBody AdjustStockRequest request) {
        return inventoryService.adjustStock(request);
    }
}
