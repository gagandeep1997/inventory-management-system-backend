package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.InventoryRequest;
import com.gagan.inventory.dto.response.InventoryResponse;

public interface InventoryService {
    InventoryResponse addStock(InventoryRequest inventoryRequest);
}
