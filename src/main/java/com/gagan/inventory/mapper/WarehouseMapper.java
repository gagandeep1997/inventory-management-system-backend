package com.gagan.inventory.mapper;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.WarehouseResponse;
import com.gagan.inventory.entity.Warehouse;

public final class WarehouseMapper {

    private WarehouseMapper() {
    }

    public static Warehouse toEntity(WarehouseRequest request) {

        Warehouse warehouse = new Warehouse();

        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());

        return warehouse;
    }

    public static WarehouseResponse toResponse(
            Warehouse warehouse
    ) {

        WarehouseResponse response =
                new WarehouseResponse();

        response.setId(warehouse.getId());
        response.setName(warehouse.getName());
        response.setLocation(warehouse.getLocation());

        return response;
    }
}