package de.gfg.ms.catalogservice.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.db.entities.ProductVersion;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductServiceIntTest {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductVersionService productVersionService;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void saveProductTest() {
		ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
		StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "RED",new BigDecimal(201.20),"TITLE_1");
		
		Product product = productService.saveProduct(storeProductRequest);
		
		assertNotNull(product);
		assertNotNull(product.getId());
		assertEquals(storeProductRequest.getColor(), product.getColor());
		assertEquals(storeProductRequest.getProductIdentifier().getBrand(), product.getBrand());
		
		ProductVersion productVersion = productVersionService.getProductVersion(productIdentifier, product.getVersionCounter());
		
		assertNull(productVersion);		
	}
	
	@Test
	public void getProductByProductIdentifierTest() {
		ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
		StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "RED",new BigDecimal(201.20),"TITLE_1");
		
		productService.saveProduct(storeProductRequest);
		
		Product product = productService.getProductByProductIdentifier(productIdentifier);
		assertNotNull(product);		
		assertNotNull(product.getId());
		assertEquals(storeProductRequest.getColor(), product.getColor());
		assertEquals(storeProductRequest.getProductIdentifier().getBrand(), product.getBrand());
		
		ProductVersion productVersion = productVersionService.getProductVersion(productIdentifier, product.getVersionCounter());
		
		assertNull(productVersion);		
	}

	@Test
	public void deleteProductTest() {
		ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
		StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "RED",new BigDecimal(201.20),"TITLE_1");
		
		productService.saveProduct(storeProductRequest);
		
		boolean deleted = productService.deleteProduct(productIdentifier);
		assertTrue(deleted);
		
		Product product = productService.getProductByProductIdentifier(productIdentifier);
		assertNull(product);		
	}
	
	@Test
	public void saveProductListTest() {
		List<StoreProductRequest> storeProductRequestList = new ArrayList<>();
		
		ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND_1");
		StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_1",new BigDecimal(12.21),"PRODUCT_TITLE_1");
		storeProductRequestList.add(storeProductRequest);
		
		productIdentifier = new ProductIdentifier(new Long(2), "PRODUCT_BRAND_2");
		storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_2", "PRODUCT_COLOR_2",new BigDecimal(21.12),"PRODUCT_TITLE_2");
		storeProductRequestList.add(storeProductRequest);
			
		List<Product> productList = productService.saveProductList(storeProductRequestList);
		
		assertNotNull(productList);
		productList.stream().forEachOrdered(p -> assertNotNull(p.getId()));
		productList.stream().forEachOrdered(p -> assertNotNull(p.getProductId()));
		productList.stream().forEachOrdered(p -> assertNotNull(p.getPrice()));
		productList.stream().forEachOrdered(p -> assertNotNull(p.getCreatedAt()));
		productList.stream().forEachOrdered(p -> assertNotNull(p.getVersionCounter()));
	}
	
	@Test
	public void listByProductListFilterAndProductSortFilterTest() {
		ProductSearchFilter productSearchFilter = new ProductSearchFilter();
		//productSearchFilter.s
	}
	
	
}
