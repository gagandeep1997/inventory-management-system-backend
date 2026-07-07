package com.gagan.inventory.mapper;

import com.gagan.inventory.dto.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public final class PageMapper {
    private PageMapper() {}

    public static <T> PageResponse<T> toPageResponse(
            Page<?> page,
            List<T> content) {

        return PageResponse
                .<T>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .lastPage(page.isLast())
                .build();
    }
}