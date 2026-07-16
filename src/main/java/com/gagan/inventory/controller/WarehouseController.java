package com.gagan.inventory.controller;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.WarehouseResponse;
import com.gagan.inventory.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Operation(summary = "Create a Warehouse")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public WarehouseResponse createWarehouse(@Valid @RequestBody WarehouseRequest request) {
        return warehouseService.createWarehouse(request);
    }

    @Operation(summary = "Get all warehouses")
    @GetMapping
    public PageResponse<WarehouseResponse> getAllWarehouses(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "name") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String direction) {
        return warehouseService.getAllWarehouses(page, size, sortBy, direction);
    }

    @Operation(summary = "Get Warehouse by ID")
    @GetMapping("/{id}")
    public WarehouseResponse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @Operation(summary = "Update an warehouse")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public WarehouseResponse updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequest request) {
        return warehouseService.updateWarehouse(id, request);
    }

    @Operation(summary = "Delete an warehouse")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
    }
}
