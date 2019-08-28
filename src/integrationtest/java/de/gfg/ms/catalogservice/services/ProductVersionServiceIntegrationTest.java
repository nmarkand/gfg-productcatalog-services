package de.gfg.ms.catalogservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.db.entities.ProductVersion;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductVersionServiceIntegrationTest {
    
    @Autowired
    ProductVersionService productVersionService;
    
    @Test
    public void saveVersionIntegrationTest() {
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(1));
        
        ProductVersion version = productVersionService.saveProductVersion(ProductVersion.forProduct(product));
        
        assertNotNull(version);
        assertNotNull(version.getId());
        assertEquals(version.getBrand(), product.getBrand());
        assertEquals(version.getProductId(), product.getProductId());
    }
    
    @Test
    public void saveProductVersionListIntegrationTest() {
        List<Product> productList = new ArrayList<>();
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(1));
        
        productList.add(product);
        
        product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(2));
        
        productList.add(product);
        
        List<ProductVersion> versionList = productVersionService.saveProductVersionList(ProductVersion.forProductList(productList));
        
        assertTrue(versionList != null && !versionList.isEmpty());
        assertNotNull(versionList.get(0).getId());
        assertNotNull(versionList.get(1).getId());
    }
    
    @Test
    public void getProductVersionsIntegrationTest() {
        List<Product> productList = new ArrayList<>();
        
        Product product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(1));
        
        productList.add(product);
        
        product = new Product();
        product.setBrand("PRODUCT_BRAND");
        product.setProductId(new Long(1));
        
        productList.add(product);
        
        productVersionService.saveProductVersionList(ProductVersion.forProductList(productList));
        
        List<ProductVersion> versions = productVersionService.getProductVersions(new ProductIdentifier(new Long(1), "PRODUCT_BRAND"));
        
        assertTrue(versions != null && !versions.isEmpty());
        assertNotNull(versions.get(0).getId());
        assertNotNull(versions.get(1).getId());
    }
}
