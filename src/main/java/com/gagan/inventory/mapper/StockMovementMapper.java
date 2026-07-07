package com.gagan.inventory.mapper;

import com.gagan.inventory.dto.response.StockMovementResponse;
import com.gagan.inventory.entity.StockMovement;

public final class StockMovementMapper {
    private StockMovementMapper() {}

    public static StockMovementResponse toResponse(StockMovement movement) {
        return StockMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProduct().getId())
                .productName(movement.getProduct().getName())
                .sku(movement.getProduct().getSku())
                .warehouseId(movement.getWarehouse().getId())
                .warehouseName(movement.getWarehouse().getName())
                .warehouseCode(movement.getWarehouse().getCode())
                .movementType(movement.getMovementType())
                .quantity(movement.getQuantity())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}
