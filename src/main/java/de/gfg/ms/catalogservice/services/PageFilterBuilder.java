package de.gfg.ms.catalogservice.services;

import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

public class PageFilterBuilder {
    private static final Integer PAGE_SIZE = 50;
    private static final Integer PAGE_COUNT = 0;
    
    public Integer getPageSize(final ProductSortFilter productSortFilter) {
        return productSortFilter != null && productSortFilter.getPageSize() != null ? productSortFilter.getPageSize() : PAGE_SIZE;
    }
    
    public Integer getPageCount(final ProductSortFilter productSortFilter) {
        return productSortFilter != null && productSortFilter.getPageCount() != null ? productSortFilter.getPageSize() : PAGE_COUNT;
    }
    
}
