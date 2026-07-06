package com.gagan.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TransferStockRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Source warehouse ID is required")
    private Long sourceWarehouseId;

    @NotNull(message = "Destination warehouse ID is required")
    private Long destinationWarehouseId;

    @NotNull(message = "Quantity is required")
    @Min(
            value = 1,
            message = "Quantity must be greater than zero"
    )
    private Integer quantity;
}
