package de.gfg.ms.catalogservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.db.entities.ProductVersion;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductServiceIntegrationTest {
    
    @Autowired
    ProductService productService;
    
    @Autowired
    ProductVersionService productVersionService;
    
    @Test
    public void saveProductIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        Product product = productService.saveProduct(storeProductRequest);
        
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(product.getTitle(), storeProductRequest.getTitle());
        assertNotNull(product.getProductIdentifier());
        assertEquals(product.getBrand(), storeProductRequest.getProductIdentifier().getBrand());
        assertEquals(product.getProductId(), storeProductRequest.getProductIdentifier().getProductId());
        assertEquals(product.getDescription(), storeProductRequest.getDescription());
        assertEquals(product.getPrice(), storeProductRequest.getPrice());
        assertEquals(product.getColor(), storeProductRequest.getColor());
        assertEquals(product.getProductVersion(), new Long(1));
        
        // No version stored when first time a product saved.
        List<ProductVersion> productVersionList = productVersionService.getProductVersions(productIdentifier);
        assertTrue(productVersionList.isEmpty());
    }
    
    @Test
    public void saveVersionIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        Product product = productService.saveProduct(storeProductRequest);
        
        productService.saveVersion(product);
        
        List<ProductVersion> productVersionList = productVersionService.getProductVersions(productIdentifier);
        assertFalse(productVersionList.isEmpty());
        
        assertNotNull(productVersionList.get(0).getId());
        assertEquals(productVersionList.get(0).getProductId(), product.getProductId());
        assertEquals(productVersionList.get(0).getBrand(), product.getBrand());
        assertEquals(productVersionList.get(0).getDescription(), product.getDescription());
        assertEquals(productVersionList.get(0).getPrice(), product.getPrice());
        assertEquals(productVersionList.get(0).getColor(), product.getColor());
        assertEquals(productVersionList.get(0).getTitle(), product.getTitle());
        assertEquals(productVersionList.get(0).getProductVersion(), new Long(1));
    }
    
    @Test
    public void getProductByProductIdentifierIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        productService.saveProduct(storeProductRequest);
        
        Product product = productService.getProductByProductIdentifier(productIdentifier);
        
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(product.getTitle(), storeProductRequest.getTitle());
        assertNotNull(product.getProductIdentifier());
        assertEquals(product.getBrand(), storeProductRequest.getProductIdentifier().getBrand());
        assertEquals(product.getProductId(), storeProductRequest.getProductIdentifier().getProductId());
        assertEquals(product.getDescription(), storeProductRequest.getDescription());
        assertEquals(product.getPrice(), storeProductRequest.getPrice());
        assertEquals(product.getColor(), storeProductRequest.getColor());
        assertEquals(product.getProductVersion(), new Long(1));
    }
    
    @Test
    public void deleteProductIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        productService.saveProduct(storeProductRequest);
        
        Boolean deleted = productService.deleteProduct(productIdentifier);
        
        assertTrue(deleted);
        
        // After deletion product should not be available in product table.
        Product product = productService.getProductByProductIdentifier(productIdentifier);
        assertNull(product);
        
        // After deletion product must be versioned and should be available in product_version table.
        List<ProductVersion> productVersionList = productVersionService.getProductVersions(productIdentifier);
        assertFalse(productVersionList.isEmpty());
        
        assertNotNull(productVersionList.get(0).getId());
        assertEquals(productVersionList.get(0).getProductId(), storeProductRequest.getProductIdentifier().getProductId());
        assertEquals(productVersionList.get(0).getBrand(), storeProductRequest.getProductIdentifier().getBrand());
        assertEquals(productVersionList.get(0).getDescription(), storeProductRequest.getDescription());
        assertEquals(productVersionList.get(0).getPrice(), storeProductRequest.getPrice());
        assertEquals(productVersionList.get(0).getColor(), storeProductRequest.getColor());
        assertEquals(productVersionList.get(0).getTitle(), storeProductRequest.getTitle());
        assertEquals(productVersionList.get(0).getProductVersion(), new Long(1));
    }
    
    @Test
    public void searchProductByProductSearchFilterAndProductSortFilterIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1",
                new BigDecimal(58.79), "PRODUCT_TITLE_1");
        
        productService.saveProduct(storeProductRequest);
        
        productIdentifier = new ProductIdentifier(new Long(2), "PRODUCT_BRAND_2");
        storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_2", new BigDecimal(79.58),
                "PRODUCT_TITLE_1");
        
        productService.saveProduct(storeProductRequest);
        
        ProductSearchFilter productSearchFilter = new ProductSearchFilter();
        productSearchFilter.setTitle(storeProductRequest.getTitle());
        
        ProductSortFilter productSortFilter = new ProductSortFilter();
        
        Page<Product> productPage = productService.searchProductByProductSearchFilterAndProductSortFilter(productSearchFilter, productSortFilter);
        
        assertNotNull(productPage);
        assertEquals(productPage.getContent().size(), 2);
        
        productSearchFilter = new ProductSearchFilter();
        productSearchFilter.setTitle(storeProductRequest.getTitle());
        
        productSortFilter = new ProductSortFilter();
        List<String> setSortProperties = new ArrayList<>();
        setSortProperties.add("price");
        productSortFilter.setSortProperties(setSortProperties);
        productSortFilter.setSortOrder("DESC");
        
        productPage = productService.searchProductByProductSearchFilterAndProductSortFilter(productSearchFilter, productSortFilter);
        
        assertNotNull(productPage);
        assertEquals(productPage.getContent().size(), 2);
        
        // Sort is based on price and order is descending
        assertEquals(productPage.getContent().get(0).getColor(), "PRODUCT_COLOR_2");
        assertEquals(productPage.getContent().get(1).getColor(), "PRODUCT_COLOR_1");
    }
    
    @Test
    public void findProductByTitleAndDescriptionIntegrationTest() {
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        productService.saveProduct(storeProductRequest);
        
        ProductSearchFilter productSearchFilter = new ProductSearchFilter();
        productSearchFilter.setTitle(storeProductRequest.getTitle());
        productSearchFilter.setDescription("product_d");
        
        List<Product> productList = productService.findProductByTitleAndDescription(productSearchFilter);
        
        assertTrue(productList != null && !productList.isEmpty());
        assertNotNull(productList.get(0).getId());
        assertEquals(productList.get(0).getProductId(), storeProductRequest.getProductIdentifier().getProductId());
        assertEquals(productList.get(0).getBrand(), storeProductRequest.getProductIdentifier().getBrand());
        assertEquals(productList.get(0).getDescription(), storeProductRequest.getDescription());
        assertEquals(productList.get(0).getPrice(), storeProductRequest.getPrice());
        assertEquals(productList.get(0).getColor(), storeProductRequest.getColor());
        assertEquals(productList.get(0).getTitle(), storeProductRequest.getTitle());
        assertEquals(productList.get(0).getProductVersion(), new Long(1));
    }
    
    @Test
    public void saveProductListIntegrationTest() {
        
        List<StoreProductRequest> storeProductRequestList = new ArrayList<>();
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1",
                new BigDecimal(58.79), "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        productIdentifier = new ProductIdentifier(new Long(2), "PRODUCT_BRAND_2");
        storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_2", new BigDecimal(79.58),
                "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
        storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1", new BigDecimal(58.79),
                "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        List<Product> productList = productService.saveProductList(storeProductRequestList);
        
        assertTrue(productList != null && !productList.isEmpty());
        
        // One product is duplicated and hence versioned.
        assertEquals(productList.size(), 2);
        
        assertNotNull(productList.get(0).getId());
        assertNotNull(productList.get(1).getId());
        assertNotNull(productList.get(0).getProductVersion());
        assertNotNull(productList.get(1).getProductVersion());
        
        // One product is duplicated and hence version stored.
        List<ProductVersion> productVersionList = productVersionService.getProductVersions(productIdentifier);
        assertTrue(productVersionList != null && !productVersionList.isEmpty());
        
        assertEquals(productVersionList.size(), 1);
        assertEquals(productVersionList.get(0).getProductId(), new Long(1));
        assertEquals(productVersionList.get(0).getProductVersion(), new Long(1));
        assertEquals(productVersionList.get(0).getDescription(), "PRODUCT_DESCRIPTION_1");
    }
}