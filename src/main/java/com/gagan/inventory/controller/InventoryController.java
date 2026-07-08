package com.gagan.inventory.controller;

import com.gagan.inventory.dto.request.AddStockRequest;
import com.gagan.inventory.dto.request.AdjustStockRequest;
import com.gagan.inventory.dto.request.RemoveStockRequest;
import com.gagan.inventory.dto.request.TransferStockRequest;
import com.gagan.inventory.dto.response.InventoryResponse;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add-stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public InventoryResponse addStock(@Valid @RequestBody AddStockRequest request) {
        return inventoryService.addStock(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/remove-stock")
    public InventoryResponse removeStock(@Valid @RequestBody RemoveStockRequest request) {
        return inventoryService.removeStock(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adjust-stock")
    public InventoryResponse adjustStock(@Valid @RequestBody AdjustStockRequest request) {
        return inventoryService.adjustStock(request);
    }

    @GetMapping("/{id}")
    public InventoryResponse getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/products/{productId}")
    public List<InventoryResponse> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @GetMapping("/warehouses/{warehouseId}")
    public List<InventoryResponse> getInventoryByWarehouse(@PathVariable Long warehouseId) {
        return inventoryService.getInventoryByWarehouse(warehouseId);
    }

    @GetMapping
    public PageResponse<InventoryResponse> getAllInventory(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "productName") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String direction) {
        return inventoryService.getAllInventory(page, size, sortBy, direction);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/transfer-stock")
    public InventoryResponse transferStock(@Valid @RequestBody TransferStockRequest request) {
        return inventoryService.transferStock(request);
    }
}
