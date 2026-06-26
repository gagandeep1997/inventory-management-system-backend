package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.ProductRequest;
import com.gagan.inventory.dto.response.ProductResponse;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.exception.ResourceAlreadyExistsException;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.mapper.ProductMapper;
import com.gagan.inventory.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        validateSkuUniqueness(null, request.getSku());

        Product product = ProductMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);

        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findProductById(id);

        validateSkuUniqueness(id, request.getSku());

        ProductMapper.updateEntity(request, product);

        Product updatedProduct =
                productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProductById(id);

        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product", id));
    }

    private void validateSkuUniqueness(Long productId, String sku) {
        productRepository.findBySku(sku)
                .filter(product ->
                        !product.getId().equals(productId))
                .ifPresent(product -> {
                    throw new ResourceAlreadyExistsException(
                            "Product",
                            "SKU",
                            sku
                    );
                });
    }
}
