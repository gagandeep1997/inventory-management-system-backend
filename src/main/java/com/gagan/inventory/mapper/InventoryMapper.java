package com.gagan.inventory.mapper;

import com.gagan.inventory.dto.response.InventoryResponse;
import com.gagan.inventory.entity.Inventory;

public final class InventoryMapper {
    private InventoryMapper() {}

    public static InventoryResponse toResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())

                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .sku(inventory.getProduct().getSku())

                .warehouseId(inventory.getWarehouse().getId())
                .warehouseName(inventory.getWarehouse().getName())
                .warehouseCode(inventory.getWarehouse().getCode())

                .quantity(inventory.getQuantity())

                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())

                .build();
    }
}
