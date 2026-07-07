package com.gagan.inventory.dto.response;

import com.gagan.inventory.entity.StockMovementType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StockMovementResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String sku;

    private Long warehouseId;

    private String warehouseName;

    private String warehouseCode;

    private StockMovementType movementType;

    private Integer quantity;

    private LocalDateTime createdAt;
}
