package de.gfg.ms.catalogservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.domain.DeleteProductResult;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.StoreProductListResponse;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.StoreProductResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductCatalogControllerUnitTest {
    
    private static final String USER_NAME = "GFG";
    private static final String PASSWORD = "productCatalog";
    
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    @Test
    public void storeProductIntegrationTest() {
        restTemplate = new TestRestTemplate(USER_NAME, PASSWORD);
        String adr = getApiAdr("/product/store");
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        HttpEntity<String> requestEntity = makeStoreProductEntity(storeProductRequest);
        ResponseEntity<StoreProductResponse> result = restTemplate.exchange(adr, HttpMethod.POST, requestEntity, StoreProductResponse.class);
        
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getDbId());
    }
    
    @Test
    public void storeProductListIntegrationTest() {
        restTemplate = new TestRestTemplate(USER_NAME, PASSWORD);
        String adr = getApiAdr("/product/storeList");
        
        List<StoreProductRequest> storeProductRequestList = new ArrayList<>();
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        StoreProductRequest storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION", "PRODUCT_COLOR",
                new BigDecimal(58.79), "PRODUCT_TITLE");
        
        storeProductRequestList.add(storeProductRequest);
        
        productIdentifier = new ProductIdentifier(new Long(2), "PRODUCT_BRAND_2");
        storeProductRequest = new StoreProductRequest(productIdentifier, "PRODUCT_DESCRIPTION_1", "PRODUCT_COLOR_2", new BigDecimal(79.58),
                "PRODUCT_TITLE_1");
        
        storeProductRequestList.add(storeProductRequest);
        
        HttpEntity<String> requestEntity = makeStoreProductListEntity(storeProductRequestList);
        ResponseEntity<StoreProductListResponse> result = restTemplate.exchange(adr, HttpMethod.POST, requestEntity, StoreProductListResponse.class);
        
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getDbIdList());
    }
    
    @Test
    public void getProductContentIntegrationTest() {
        restTemplate = new TestRestTemplate(USER_NAME, PASSWORD);
        String adr = getApiAdr("/product/content");
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        
        ParameterizedTypeReference<Product> responseType = new ParameterizedTypeReference<Product>() {
        };
        
        HttpEntity<String> requestEntityProductIdentifier = makeProductIdentifierEntity(productIdentifier);
        
        ResponseEntity<Product> result = restTemplate.exchange(adr, HttpMethod.POST, requestEntityProductIdentifier, responseType);
        
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    @Test
    public void deleteProductIntegrationTest() {
        restTemplate = new TestRestTemplate(USER_NAME, PASSWORD);
        String adr = getApiAdr("/product/delete");
        
        ProductIdentifier productIdentifier = new ProductIdentifier(new Long(1), "PRODUCT_BRAND");
        
        ParameterizedTypeReference<DeleteProductResult> responseType = new ParameterizedTypeReference<DeleteProductResult>() {
        };
        
        HttpEntity<String> requestEntityProductIdentifier = makeProductIdentifierEntity(productIdentifier);
        
        ResponseEntity<DeleteProductResult> result = restTemplate.exchange(adr, HttpMethod.POST, requestEntityProductIdentifier, responseType);
        
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    private HttpEntity<String> makeProductIdentifierEntity(final ProductIdentifier productIdentifier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        return new HttpEntity<>(data2json(productIdentifier), headers);
    }
    
    private String getApiAdr(String apiPath) {
        return "http://localhost:" + port + apiPath;
    }
    
    private HttpEntity<String> makeStoreProductEntity(final StoreProductRequest storeProductRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        return new HttpEntity<>(data2json(storeProductRequest), headers);
    }
    
    private HttpEntity<String> makeStoreProductListEntity(final List<StoreProductRequest> storeProductRequestList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        return new HttpEntity<>(data2json(storeProductRequestList), headers);
    }
    
    private String data2json(Object data) {
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
