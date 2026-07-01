package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.WarehouseResponse;
import com.gagan.inventory.entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    List<WarehouseResponse> getAllWarehouses();

    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);

    void deleteWarehouse(Long id);
}
