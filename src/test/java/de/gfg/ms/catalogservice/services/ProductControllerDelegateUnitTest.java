package de.gfg.ms.catalogservice.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;

import de.gfg.ms.catalogservice.ctrl.ProductControllerDelegate;
import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.domain.DeleteProductResult;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchAndSortFilterRequest;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductListResponse;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.StoreProductResponse;
import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

public class ProductControllerDelegateUnitTest {
    
    ProductService productService = null;
    ProductControllerDelegate deligateController;
    
    @org.junit.Before
    public void setUp() throws Exception {
        productService = mock(ProductService.class);
        deligateController = new ProductControllerDelegate(productService);
    }
    
    @Test
    public void storeProductTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(1));
        
        when(productService.saveProduct(storeProductRequest)).thenReturn(product);
        
        StoreProductResponse response = deligateController.storeProduct(storeProductRequest);
        assertNotNull(response);
        assertNotNull(response.getDbId());
        
    }
    
    @Test
    public void storeProductListTest() {
        List<StoreProductRequest> storeProductRequestList = new ArrayList<>();
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1",
                new BigDecimal(58.79), "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        productIdentifier = new ProductIdentifier(new Long(2), "PRODUCT_BRAND_2");
        storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_2", new BigDecimal(79.58),
                "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        List<Product> productList = new ArrayList<>();
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND_1");
        product.setProductId(new Long(1));
        
        productList.add(product);
        
        product = new Product();
        product.setBrand("PRODUCT_BRAND_2");
        product.setProductId(new Long(2));
        
        productList.add(product);
        
        when(productService.saveProductList(storeProductRequestList)).thenReturn(productList);
        
        StoreProductListResponse response = deligateController.storeProductList(storeProductRequestList);
        assertTrue(response != null && response.getDbIdList() != null && !response.getDbIdList().isEmpty());
        assertNotNull(response.getDbIdList());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void searchProductByProductSearchAndSortFilterRequestTest() {
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1",
                new BigDecimal(58.79), "PRODUCT_TITLE_1");
        
        ProductSearchFilter productSearchFilter = new ProductSearchFilter();
        productSearchFilter.setTitle(storeProductRequest.getTitle());
        productSearchFilter.setDescription(storeProductRequest.getDescription());
        
        ProductSortFilter productSortFilter = new ProductSortFilter();
        List<String> setSortProperties = new ArrayList<>();
        setSortProperties.add("price");
        productSortFilter.setSortProperties(setSortProperties);
        productSortFilter.setSortOrder("DESC");
        
        ProductSearchAndSortFilterRequest filterRequest = new ProductSearchAndSortFilterRequest();
        filterRequest.setProductSearchFilter(productSearchFilter);
        filterRequest.setProductSortFilter(productSortFilter);
        

        Page<Product> productPage = mock(Page.class);
        
        when(productService.searchProductByProductSearchFilterAndProductSortFilter(productSearchFilter, productSortFilter)).thenReturn(productPage);
        
        assertNotNull(deligateController.searchProductByProductSearchAndSortFilterRequest(filterRequest));
    }
    
    @Test
    public void findProductByTitleAndDescriptionTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        ProductSearchFilter productSearchFilter = new ProductSearchFilter();
        productSearchFilter.setTitle(storeProductRequest.getTitle());
        productSearchFilter.setDescription(storeProductRequest.getDescription());
        
        List<Product> productList = new ArrayList<>();
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND_1");
        product.setProductId(new Long(1));
        
        productList.add(product);
        
        product = new Product();
        product.setBrand("PRODUCT_BRAND_2");
        product.setProductId(new Long(2));
        productList.add(product);
        
        when(productService.findProductByTitleAndDescription(productSearchFilter)).thenReturn(productList);
        
        List<Product> products = deligateController.findProductByTitleAndDescription(productSearchFilter);
        assertTrue(products != null && !products.isEmpty());
    }
    
    @Test
    public void getProductTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND_1");
        product.setProductId(new Long(1));
        
        when(productService.getProductByProductIdentifier(productIdentifier)).thenReturn(product);
        
        Product p = deligateController.getProduct(productIdentifier);
        assertTrue(p != null);
    }
    
    @Test
    public void deleteProductTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        
        Boolean deleted = true;
        
        when(productService.deleteProduct(productIdentifier)).thenReturn(deleted);
        DeleteProductResult result = deligateController.deleteProduct(productIdentifier);
        assertTrue(result.isDeleted());
    }
}
