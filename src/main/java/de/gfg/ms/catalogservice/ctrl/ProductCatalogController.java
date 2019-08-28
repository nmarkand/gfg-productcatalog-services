package de.gfg.ms.catalogservice.ctrl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.domain.DeleteProductResult;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchAndSortFilterRequest;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductListResponse;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.StoreProductResponse;

@RestController
@RequestMapping("/product")
public class ProductCatalogController {
    
    private ProductControllerDelegate delegate;
    
    private static final Logger log = LoggerFactory.getLogger(ProductCatalogController.class);
    
    @Autowired
    public ProductCatalogController(ProductControllerDelegate delegate) {
        this.delegate = delegate;
    }
 
    @RequestMapping(path = "store", method = RequestMethod.POST)
    public ResponseEntity<StoreProductResponse> storeProduct(@RequestBody StoreProductRequest storeRequest) {
        StoreProductResponse response = delegate.storeProduct(storeRequest);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST)
    public ResponseEntity<Page<Product>> searchProducts(@RequestBody ProductSearchAndSortFilterRequest productSearchAndSortFilterRequest) {
        log.info("searchProducts");
        Page<Product> products = delegate.searchProductByProductSearchAndSortFilterRequest(productSearchAndSortFilterRequest);
        log.info("got products, returning list");
        return ResponseEntity.ok().body(products);
    }
    
    @RequestMapping(path = "searchByTitleAndDescription", method = RequestMethod.POST)
    public ResponseEntity<List<Product>> searchProductsByTitleAndDescription(@RequestBody ProductSearchFilter productSearchFilter) {
        log.info("searchProductsByTitleAndDescription");
        List<Product> products = delegate.findProductByTitleAndDescription(productSearchFilter);
        		log.info("got products, returning list");
        return ResponseEntity.ok().body(products);
    }
    
    @RequestMapping(path = "content", method = RequestMethod.POST)
    public ResponseEntity<Product> getProductContent(@RequestBody ProductIdentifier productIdentifier) {
        log.info("get product for " + productIdentifier);
        Product product = delegate.getProduct(productIdentifier);
        log.info("got product, returning data" + productIdentifier);
        return ResponseEntity.ok().body(product);
    }
    
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public ResponseEntity<DeleteProductResult> deleteProduct(@RequestBody ProductIdentifier productIdentifier) {
        log.info("deleteProduct with " + productIdentifier);
        DeleteProductResult result = delegate.deleteProduct(productIdentifier);
        log.info("product deleted");
        return ResponseEntity.ok().body(result);
    }
    
    @RequestMapping(path = "storeList", method = RequestMethod.POST)
    public ResponseEntity<StoreProductListResponse> storeProductList(@RequestBody List<StoreProductRequest> storeProductRequestList) {
    	log.info("storeProductList");  
    	StoreProductListResponse responseList = delegate.storeProductList(storeProductRequestList);
    	log.info("product list stored");
    	return ResponseEntity.ok().body(responseList);
    }
}
