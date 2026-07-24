package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.ProductRequest;
import com.gagan.inventory.dto.response.PageResponse;
import com.gagan.inventory.dto.response.ProductResponse;
import com.gagan.inventory.entity.Product;
import com.gagan.inventory.exception.ResourceAlreadyExistsException;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    String Sku = "001";

    @Test
    void shouldCreateProductSuccessfully() {
        // Arrange
        ProductRequest productRequest = createProductRequest();
        Product savedProduct = createSavedProduct();

        when(productRepository.findBySku(Sku)).
                thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class))).
                thenReturn(savedProduct);

        // Act
        ProductResponse response = productService.createProduct(productRequest);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(productRequest.getName(), response.getName()),
                () -> assertEquals(productRequest.getDescription(), response.getDescription()),
                () -> assertEquals(productRequest.getSku(), response.getSku()),
                () -> assertEquals(productRequest.getPrice(), response.getPrice()),
                () -> assertEquals(1L, response.getId())
        );

        verify(productRepository).findBySku(Sku);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product capturedProduct = captor.getValue();

        assertAll(
                () -> assertEquals(productRequest.getName(), capturedProduct.getName()),
                () -> assertEquals(productRequest.getDescription(), capturedProduct.getDescription()),
                () -> assertEquals(productRequest.getSku(), capturedProduct.getSku()),
                () -> assertEquals(productRequest.getPrice(), capturedProduct.getPrice())
        );
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenSkuAlreadyExists() {
        // Arrange
        ProductRequest productRequest = createProductRequest();
        Product savedProduct = createSavedProduct();

        when(productRepository.findBySku(Sku)).thenReturn(Optional.of(savedProduct));

        // Act
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> productService.createProduct(productRequest));

        // Assert
        assertEquals("Product already exists with SKU: 001", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
        verify(productRepository).findBySku(Sku);
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product savedProduct = createSavedProduct();

        when(productRepository.findById(productId)).thenReturn(Optional.of(savedProduct));

        // Act
        ProductResponse response = productService.getProductById(productId);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(savedProduct.getId(), response.getId()),
                () -> assertEquals(savedProduct.getName(), response.getName()),
                () -> assertEquals(savedProduct.getDescription(), response.getDescription()),
                () -> assertEquals(savedProduct.getSku(), response.getSku()),
                () -> assertEquals(savedProduct.getPrice(), response.getPrice())
        );

        verify(productRepository).findById(productId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenProductDoesNotExist () {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(productId));

        // Assert
        assertEquals("Product not found with id: 1", exception.getMessage());

        verify(productRepository).findById(productId);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        ProductRequest productRequest = createProductRequest();
        Product existingProduct = createExistingProduct(null, null);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.findBySku(productRequest.getSku()))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class)))
                .thenReturn(existingProduct);

        // Act
        ProductResponse response = productService.updateProduct(productId, productRequest);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(productRequest.getName(), response.getName()),
                () -> assertEquals(productRequest.getDescription(), response.getDescription()),
                () -> assertEquals(productRequest.getSku(), response.getSku()),
                () -> assertEquals(productRequest.getPrice(), response.getPrice())
        );

        verify(productRepository).findById(productId);
        verify(productRepository).findBySku(productRequest.getSku());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product capturedProduct = captor.getValue();

        assertAll(
                () -> assertEquals(existingProduct.getId(), capturedProduct.getId()),
                () -> assertEquals(productRequest.getName(), capturedProduct.getName()),
                () -> assertEquals(productRequest.getDescription(), capturedProduct.getDescription()),
                () -> assertEquals(productRequest.getSku(), capturedProduct.getSku()),
                () -> assertEquals(productRequest.getPrice(), capturedProduct.getPrice())
        );
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentProduct() {
        // Arrange
        Long productId = 1L;
        ProductRequest productRequest = createProductRequest();
        when(productRepository.findById(productId)).
                thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(productId, productRequest));

        // Assert
        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(productRepository, never()).findBySku(productRequest.getSku());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionForUpdateWhenSkuBelongsToAnotherProduct() {
        // Arrange
        Long productId = 2L;
        ProductRequest productRequest = createProductRequest();
        Product existingProduct = createExistingProduct(productId, "002");
        Product anotherProduct = createExistingProduct(1L, productRequest.getSku());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.findBySku(productRequest.getSku())).thenReturn(Optional.of(anotherProduct));

        // Act
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> productService.updateProduct(productId, productRequest));

        // Assert
        assertEquals("Product already exists with SKU: 001", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(productRepository).findBySku(productRequest.getSku());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = createExistingProduct(null, null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository).findById(productId);
        verify(productRepository).delete(existingProduct);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistentProduct() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct(productId));

        // Assert
        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void shouldGetAllProductsSuccessfully() {
        // Arrange
        int page = 1, size = 1;
        String sortBy = "name", direction = "ASC";

        Product product1 = createExistingProduct(1L, "001");
        Product product2 = createExistingProduct(2L, "002");

        List<Product> products = List.of(product1, product2);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));

        Page<Product> pageProducts = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(any(Pageable.class))).thenReturn(pageProducts);

        // Act
        PageResponse<ProductResponse> pageResponse =
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        direction
                );

        // Assert
        assertAll(
                () -> assertEquals(products.size(), pageResponse.getContent().size()),
                () -> assertEquals(product1.getName(), pageResponse.getContent().get(0).getName()),
                () -> assertEquals(product2.getName(), pageResponse.getContent().get(1).getName()),
                () -> assertEquals(products.size(), pageResponse.getTotalElements()),
                () -> assertEquals(2, pageResponse.getTotalPages()),
                () -> assertEquals(page, pageResponse.getPageNumber()),
                () -> assertEquals(size, pageResponse.getPageSize())
        );

        verify(productRepository).findAll(any(Pageable.class));
    }

    private ProductRequest createProductRequest() {
        ProductRequest request = new ProductRequest();

        request.setName("Product Name 1");
        request.setDescription("Product Description 1");
        request.setSku("001");
        request.setPrice(BigDecimal.valueOf(1));

        return request;
    }

    private Product createSavedProduct() {
        Product savedProduct = new Product();

        savedProduct.setId(1L);
        savedProduct.setName("Product Name 1");
        savedProduct.setDescription("Product Description 1");
        savedProduct.setSku("001");
        savedProduct.setPrice(BigDecimal.valueOf(1));

        return savedProduct;
    }

    private Product createExistingProduct(Long productId, String sku) {
        Product existingProduct = new Product();

        existingProduct.setId(productId != null ? productId : 1L);
        existingProduct.setName("Product Name 2");
        existingProduct.setDescription("Product Description 2");
        existingProduct.setSku((sku != null && !sku.isEmpty()) ? sku : "001");
        existingProduct.setPrice(BigDecimal.valueOf(2));

        return existingProduct;
    }
}
