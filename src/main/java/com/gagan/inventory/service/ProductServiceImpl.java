package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.ProductRequest;
import com.gagan.inventory.dto.response.ProductResponse;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.exception.ProductAlreadyExistsException;
import com.gagan.inventory.exception.ProductNotFoundException;
import com.gagan.inventory.mapper.ProductMapper;
import com.gagan.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if(productRepository.existsBySku(request.getSku())) {
            throw new ProductAlreadyExistsException(request.getSku());
        }
        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
