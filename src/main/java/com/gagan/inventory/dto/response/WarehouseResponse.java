package com.gagan.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WarehouseResponse {
    private Long id;

    private String name;

    private String code;

    private String address;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
