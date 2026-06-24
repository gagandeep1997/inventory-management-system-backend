package com.gagan.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseRequest {
    @NotBlank(message = "Warehouse name is required")
    @Size(max = 50, message = "Warehouse name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Warehouse location is required")
    @Size(max = 255, message = "Warehouse location must not exceed 255 characters")
    private String location;
}
