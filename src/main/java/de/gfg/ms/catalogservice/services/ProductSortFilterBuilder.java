package de.gfg.ms.catalogservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import de.gfg.ms.catalogservice.domain.sort.ProductSortCriteria;
import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

public class ProductSortFilterBuilder {
    
    private static final String DEFAULT_SORT_PROPERTY = "id";
    
    public Sort forProductSortFilter(final ProductSortFilter productSortFilter) {
        List<String> sortProperties = new ArrayList<>();
        
        if (productSortFilter != null && productSortFilter.getSortProperties() != null && !productSortFilter.getSortProperties().isEmpty()) {
            productSortFilter.getSortProperties().stream().forEachOrdered(p -> Arrays.asList(ProductSortCriteria.values()).forEach(q -> {
                if (q.toString().equalsIgnoreCase(p)) {
                    sortProperties.add(q.toString());
                }
            }));
        } else {
            sortProperties.add(DEFAULT_SORT_PROPERTY);
        }
        
        Sort.Direction SORT_DIRECTION = toSortDirection(productSortFilter);
        
        return new Sort(SORT_DIRECTION, sortProperties);
    }
    
    private Direction toSortDirection(final ProductSortFilter productSortFilter) {
        return productSortFilter.getSortOrder() != null && !productSortFilter.getSortOrder().isEmpty()
                && !"ASC".equalsIgnoreCase(productSortFilter.getSortOrder())
                ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
