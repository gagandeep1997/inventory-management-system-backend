package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.ProductRequest;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    PageResponse<ProductResponse> getAllProducts(int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction);
    ProductResponse updateProduct(Long id ,ProductRequest request);
    void deleteProduct(Long id);
}
