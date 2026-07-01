package com.gagan.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InventoryResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String sku;

    private Long warehouseId;

    private String warehouseName;

    private String warehouseCode;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}