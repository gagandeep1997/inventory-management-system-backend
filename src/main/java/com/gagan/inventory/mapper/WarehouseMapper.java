package com.gagan.inventory.mapper;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.WarehouseResponse;
import com.gagan.inventory.entity.Warehouse;

public final class WarehouseMapper {

    private WarehouseMapper() {
    }

    public static Warehouse toEntity(WarehouseRequest request) {

        Warehouse warehouse = new Warehouse();

        WarehouseSetter(warehouse, request);

        return warehouse;
    }

    public static WarehouseResponse toResponse(
            Warehouse warehouse
    ) {
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .code(warehouse.getCode())
                .address(warehouse.getAddress())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .country(warehouse.getCountry())
                .postalCode(warehouse.getPostalCode())
                .active(warehouse.getActive())
                .createdAt(warehouse.getCreatedAt())
                .updatedAt(warehouse.getUpdatedAt())
                .build();
    }

    public static void updateEntity(
            Warehouse warehouse,
            WarehouseRequest request
    ) {
        WarehouseSetter(warehouse, request);
    }

    private static void WarehouseSetter(Warehouse warehouse, WarehouseRequest request) {
        warehouse.setName(request.getName());
        warehouse.setCode(request.getCode());
        warehouse.setAddress(request.getAddress());
        warehouse.setCity(request.getCity());
        warehouse.setState(request.getState());
        warehouse.setCountry(request.getCountry());
        warehouse.setPostalCode(request.getPostalCode());
    }

}