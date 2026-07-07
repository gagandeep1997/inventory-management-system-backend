package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.WarehouseResponse;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    PageResponse<WarehouseResponse> getAllWarehouses(int page,
                                                     int size,
                                                     String sortBy,
                                                     String direction);

    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);

    void deleteWarehouse(Long id);
}
