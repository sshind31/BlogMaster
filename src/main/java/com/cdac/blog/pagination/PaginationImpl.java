package com.cdac.blog.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaginationImpl implements Pagination {

    @Override
    public Pageable getPageable(int pageNo, int pageSize, String sortField, Optional<String> order) {
        return order.map(s -> PageRequest.of(pageNo, pageSize, s.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending())).orElseGet(() -> PageRequest.of(pageNo, pageSize));
    }
}
