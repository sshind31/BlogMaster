package com.cdac.blog.pagination;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Pagination {
    Pageable getPageable(int pageNo, int pageSize, String sortField, Optional<String> order);
}
