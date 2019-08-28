package de.gfg.ms.catalogservice.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;

public class ProductFilterBuilder {

private static final List<String> IGNORED_PROPERTY_LIST = (List<String>) Arrays.asList(new String[] { "id", "productVersion" });
    
    public Example<Product> getExampleProductByProductListFilter(final ProductSearchFilter productSearchFilter) {
        final Product product = forProductListFilter(productSearchFilter);
        return Example.of(product, getExampleMatcher(IGNORED_PROPERTY_LIST));
    }
    
    private ExampleMatcher getExampleMatcher(final List<String> ignoredProperties) {
        return ExampleMatcher.matchingAny().withIgnoreCase().withIgnoreNullValues().withIgnorePaths(ignoredProperties.toArray(new String[ignoredProperties.size()]));
    }
    
    private Product forProductListFilter(final ProductSearchFilter productSearchFilter) {
        Product product = new Product();
        product.setTitle(productSearchFilter.getTitle());
        product.setDescription(productSearchFilter.getDescription());
        return product;
    }
}
