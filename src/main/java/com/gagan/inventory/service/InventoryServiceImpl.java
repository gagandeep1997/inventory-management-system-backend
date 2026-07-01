package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.InventoryRequest;
import com.gagan.inventory.dto.response.InventoryResponse;
import com.gagan.inventory.entity.Inventory;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.entity.Warehouse;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.mapper.InventoryMapper;
import com.gagan.inventory.repository.InventoryRepository;
import com.gagan.inventory.repository.ProductRepository;
import com.gagan.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(ProductRepository productRepository,
                                WarehouseRepository warehouseRepository,
                                InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponse addStock(InventoryRequest request) {
        Product product = findProductById(request.getProductId());
        Warehouse warehouse = findWarehouseById(request.getWarehouseId());

        Optional<Inventory> existingInventory = inventoryRepository.findByProductAndWarehouse(product, warehouse);

        Inventory inventory;

        if (existingInventory.isPresent()) {
            inventory = existingInventory.get();
            inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        } else {
            inventory = new Inventory();

            inventory.setProduct(product);
            inventory.setWarehouse(warehouse);
            inventory.setQuantity(request.getQuantity());
        }

        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toResponse(savedInventory);
    }

    private Warehouse findWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse",
                                id));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                id));
    }
}
