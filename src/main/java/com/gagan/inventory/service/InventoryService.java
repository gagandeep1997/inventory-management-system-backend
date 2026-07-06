package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.AddStockRequest;
import com.gagan.inventory.dto.request.AdjustStockRequest;
import com.gagan.inventory.dto.request.RemoveStockRequest;
import com.gagan.inventory.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse addStock(AddStockRequest request);
    InventoryResponse removeStock(RemoveStockRequest request);
    InventoryResponse adjustStock(AdjustStockRequest request);
    InventoryResponse getInventoryById(Long id);
    List<InventoryResponse> getInventoryByProduct(Long productId);
    List<InventoryResponse> getInventoryByWarehouse(Long warehouseId);
    List<InventoryResponse> getAllInventory();
}
