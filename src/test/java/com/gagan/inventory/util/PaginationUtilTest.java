package com.gagan.inventory.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaginationUtilTest {
    @Test
    void shouldCreatePageableWithAscending() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        String direction = "ASC";

        // Act
        Pageable result = PaginationUtil.createPageable(page, size, sortBy, direction);

        // Assert
        assertAll(
                () -> assertEquals(page, result.getPageNumber()),
                () -> assertEquals(size, result.getPageSize()),
                () -> assertEquals(Sort.Direction.ASC, Objects.requireNonNull(result.getSort().getOrderFor(sortBy)).getDirection())
        );
    }

    @Test
    void shouldCreatePageableWithDescending() {
        // Arrange
        int page = 1;
        int size = 10;
        String sortBy = "name";
        String direction = "DESC";

        // Act
        Pageable result = PaginationUtil.createPageable(page, size, sortBy, direction);

        // Assert
        assertAll(
                () -> assertEquals(page, result.getPageNumber()),
                () -> assertEquals(size, result.getPageSize()),
                () -> assertEquals(Sort.Direction.DESC, Objects.requireNonNull(result.getSort().getOrderFor(sortBy)).getDirection())
        );
    }

}
