package com.gagan.inventory.controller;

import com.gagan.inventory.dto.request.AddStockRequest;
import com.gagan.inventory.dto.request.AdjustStockRequest;
import com.gagan.inventory.dto.request.RemoveStockRequest;
import com.gagan.inventory.dto.request.TransferStockRequest;
import com.gagan.inventory.dto.response.InventoryResponse;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Add a new stock")
    @PostMapping("/add-stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public InventoryResponse addStock(@Valid @RequestBody AddStockRequest request) {
        return inventoryService.addStock(request);
    }

    @Operation(summary = "Remove a stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/remove-stock")
    public InventoryResponse removeStock(@Valid @RequestBody RemoveStockRequest request) {
        return inventoryService.removeStock(request);
    }

    @Operation(summary = "Adjust an stock")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adjust-stock")
    public InventoryResponse adjustStock(@Valid @RequestBody AdjustStockRequest request) {
        return inventoryService.adjustStock(request);
    }

    @Operation(summary = "Get Inventory by ID")
    @GetMapping("/{id}")
    public InventoryResponse getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @Operation(summary = "Get Inventory By Product Id")
    @GetMapping("/products/{productId}")
    public List<InventoryResponse> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @Operation(summary = "Get Inventory By Warehouse Id")
    @GetMapping("/warehouses/{warehouseId}")
    public List<InventoryResponse> getInventoryByWarehouse(@PathVariable Long warehouseId) {
        return inventoryService.getInventoryByWarehouse(warehouseId);
    }

    @Operation(summary = "Get all Inventories")
    @GetMapping
    public PageResponse<InventoryResponse> getAllInventory(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "productName") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String direction) {
        return inventoryService.getAllInventory(page, size, sortBy, direction);
    }

    @Operation(summary = "Transfer an stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/transfer-stock")
    public InventoryResponse transferStock(@Valid @RequestBody TransferStockRequest request) {
        return inventoryService.transferStock(request);
    }
}
