package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.ProductRequest;
import com.gagan.inventory.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long id ,ProductRequest request);
    void deleteProduct(Long id);
}
