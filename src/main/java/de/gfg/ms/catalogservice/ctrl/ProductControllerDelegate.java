package de.gfg.ms.catalogservice.ctrl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.domain.DeleteProductResult;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchAndSortFilterRequest;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductListResponse;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.StoreProductResponse;
import de.gfg.ms.catalogservice.services.ProductService;

@Service
public class ProductControllerDelegate {
    
    private ProductService productService;
    
    @Autowired
    public ProductControllerDelegate(ProductService productService) {
        this.productService = productService;
    }
    
    public StoreProductResponse storeProduct(final StoreProductRequest storeProductRequest) {
        StoreProductResponse response = new StoreProductResponse();
        Product product = productService.saveProduct(storeProductRequest);
        response.setDbId(product.getId());
        return response;
    }
    
    public StoreProductListResponse storeProductList(final List<StoreProductRequest> storeProductRequestList) {
        List<Product> productList = productService.saveProductList(storeProductRequestList);
        return new StoreProductListResponse(productList.stream().map(Product::getId).collect(Collectors.toList()));
    }
    
    public Page<Product> searchProductByProductSearchAndSortFilterRequest(final ProductSearchAndSortFilterRequest productSearchAndSortFilterRequest) {
        return productService.searchProductByProductSearchFilterAndProductSortFilter(productSearchAndSortFilterRequest.getProductSearchFilter(),
                productSearchAndSortFilterRequest.getProductSortFilter());
    }
    
    public List<Product> findProductByTitleAndDescription(final ProductSearchFilter productSearchFilter) {
        return productService.findProductByTitleAndDescription(productSearchFilter);
    }
    
    public Product getProduct(ProductIdentifier productIdentifier) {
        return productService.getProductByProductIdentifier(productIdentifier);
    }
    
    public DeleteProductResult deleteProduct(ProductIdentifier productIdentifier) {
        boolean deleted = productService.deleteProduct(productIdentifier);
        return new DeleteProductResult(deleted);
    }
}
