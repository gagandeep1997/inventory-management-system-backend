package com.gagan.inventory.service;

import com.gagan.inventory.dto.response.StockMovementResponse;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.entity.StockMovement;
import com.gagan.inventory.entity.StockMovementType;
import com.gagan.inventory.entity.Warehouse;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.mapper.StockMovementMapper;
import com.gagan.inventory.repository.ProductRepository;
import com.gagan.inventory.repository.StockMovementRepository;
import com.gagan.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockMovementServiceImpl implements StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository,
                                    ProductRepository productRepository,
                                    WarehouseRepository warehouseRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void recordMovement(Product product,
                               Warehouse warehouse,
                               StockMovementType movementType,
                               Integer quantity) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setWarehouse(warehouse);
        stockMovement.setMovementType(movementType);
        stockMovement.setQuantity(quantity);

        stockMovementRepository.save(stockMovement);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StockMovementResponse> getAllStockMovements() {
        return stockMovementRepository
                .findAll()
                .stream()
                .map(StockMovementMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StockMovementResponse> getStockMovementsByProduct(Long productId) {
        Product product = findProductById(productId);

        return stockMovementRepository
                .findByProduct(product)
                .stream()
                .map(StockMovementMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StockMovementResponse> getStockMovementsByWarehouse(Long warehouseId) {
        Warehouse warehouse = findWarehouseById(warehouseId);

        return stockMovementRepository
                .findByWarehouse(warehouse)
                .stream()
                .map(StockMovementMapper::toResponse)
                .toList();
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                productId
                        ));
    }

    private Warehouse findWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse",
                                warehouseId
                        ));
    }
}
