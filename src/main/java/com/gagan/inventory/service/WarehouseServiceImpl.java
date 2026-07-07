package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.WarehouseRequest;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.WarehouseResponse;
import com.gagan.inventory.entity.Warehouse;
import com.gagan.inventory.exception.ResourceAlreadyExistsException;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.mapper.PageMapper;
import com.gagan.inventory.mapper.WarehouseMapper;
import com.gagan.inventory.repository.WarehouseRepository;
import com.gagan.inventory.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        validateCodeUniqueness(null, request.getCode());

        Warehouse warehouse = WarehouseMapper.toEntity(request);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return WarehouseMapper.toResponse(savedWarehouse);
    }

    @Transactional(readOnly = true)
    @Override
    public WarehouseResponse getWarehouseById(Long id) {
        Warehouse warehouse = findWarehouseById(id);

        return WarehouseMapper.toResponse(warehouse);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<WarehouseResponse> getAllWarehouses(int page, int size, String sortBy, String direction) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sortBy, direction);

        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageable);

        List<WarehouseResponse> content = warehousePage.getContent()
                .stream()
                .map(WarehouseMapper::toResponse)
                .toList();

        return PageMapper.toPageResponse(warehousePage, content);
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = findWarehouseById(id);

        validateCodeUniqueness(id, request.getCode());

        WarehouseMapper.updateEntity(warehouse, request);

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        return WarehouseMapper.toResponse(updatedWarehouse);
    }

    @Override
    public void deleteWarehouse(Long id) {
        Warehouse warehouse = findWarehouseById(id);

        warehouseRepository.delete(warehouse);
    }

    private Warehouse findWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Warehouse", id));
    }

    private void validateCodeUniqueness(Long warehouseId, String code) {
        Optional<Warehouse> existingWarehouse =
                warehouseRepository.findByCode(code);

        if (existingWarehouse.isPresent()
                && (warehouseId == null ||
                !existingWarehouse.get()
                        .getId()
                        .equals(warehouseId))) {

            throw new ResourceAlreadyExistsException(
                    "Warehouse",
                    "Code",
                    code
            );
        }
    }
}
