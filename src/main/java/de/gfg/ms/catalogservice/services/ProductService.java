package de.gfg.ms.catalogservice.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.gfg.ms.catalogservice.db.entities.Product;
import de.gfg.ms.catalogservice.db.entities.ProductVersion;
import de.gfg.ms.catalogservice.db.repos.ProductRepository;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;
import de.gfg.ms.catalogservice.domain.ProductSearchFilter;
import de.gfg.ms.catalogservice.domain.StoreProductRequest;
import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    private ProductVersionService productVersionService;
    
    @Autowired
    public ProductService(ProductRepository productRepository, ProductVersionService productVersionService) {
        this.productRepository = productRepository;
        this.productVersionService = productVersionService;
    }
    
    public Product saveProduct(final StoreProductRequest storeProductRequest) {
        Product product = getProductByProductIdentifier(storeProductRequest.getProductIdentifier());
        if (product == null) {
            product = new Product(storeProductRequest.getProductIdentifier());
        }
        
        if (product.getProductVersion() > 0L) {
            saveVersion(product);
            product.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
        }
        
        product.setProductVersion(product.getProductVersion() + 1L);
        product.setDescription(storeProductRequest.getDescription());
        product.setColor(storeProductRequest.getColor());
        product.setTitle(storeProductRequest.getTitle());
        product.setPrice(storeProductRequest.getPrice());
        
        productRepository.save(product);
        return product;
    }
    
    public void saveVersion(final Product product) {
        ProductVersion version = ProductVersion.forProduct(product);
        productVersionService.saveProductVersion(version);
    }
    
    public Product getProductByProductIdentifier(ProductIdentifier productIdentifier) {
        return productRepository.findProductByProductIdAndBrand(productIdentifier.getProductId(), productIdentifier.getBrand());
    }
    
    public boolean deleteProduct(ProductIdentifier productIdentifier) {
        Product product = getProductByProductIdentifier(productIdentifier);
        saveVersion(product);
        productRepository.delete(product);
        return true;
    }
    
    public Page<Product> searchProductByProductSearchFilterAndProductSortFilter(final ProductSearchFilter productSearchFilter,
            final ProductSortFilter productSortFilter) {
        ProductFilterBuilder productFilterBuilder = new ProductFilterBuilder();
        Example<Product> productExample = productFilterBuilder
                .getExampleProductByProductListFilter(productSearchFilter);
        
        ProductSortFilterBuilder sortBuilder = new ProductSortFilterBuilder();
        Sort productSort = sortBuilder.forProductSortFilter(productSortFilter);
        
        PageFilterBuilder pageFilterBuilder = new PageFilterBuilder();
        
        Pageable paging = PageRequest.of(pageFilterBuilder.getPageCount(productSortFilter), pageFilterBuilder.getPageSize(productSortFilter),
                productSort);
        
        return productRepository.findAll(productExample, paging);
    }
    
    public List<Product> findProductByTitleAndDescription(final ProductSearchFilter productSearchFilter) {
        return productRepository.findProductListByTitleAndDescriptionContainingIgnoreCase(productSearchFilter.getTitle(), productSearchFilter.getDescription());
    }
    
    public List<Product> saveProductList(final List<StoreProductRequest> storeProductRequestList) {
        List<Product> productList = new ArrayList<>();
        List<ProductVersion> productVersionList = new ArrayList<>();
        
        storeProductRequestList.stream().forEachOrdered(p -> {
            Product product = getProductByProductIdentifier(p.getProductIdentifier());
            if (product == null) {
                product = new Product(p.getProductIdentifier());
            }
            
            List<Product> filteredProductList = filterProductListByProductIdentifier(productList, p);
            
            refreshProductAndProductVersionList(productList, productVersionList, filteredProductList);
            
            List<ProductVersion> filteredProductVersionList = filterProductVersionByProductIdentifier(
                    productVersionList, p);
            
            addToProductVersionList(productVersionList, product, filteredProductVersionList);
            
            setProductVersion(product, filteredProductVersionList);
            product.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
            product.setDescription(p.getDescription());
            product.setColor(p.getColor());
            product.setTitle(p.getTitle());
            product.setPrice(p.getPrice());
            
            productList.add(product);
        });
        
        productRepository.saveAll(productList);
        productVersionService.saveProductVersionList(productVersionList);
        return productList;
    }
    
    private void addToProductVersionList(List<ProductVersion> productVersionList, Product product,
            List<ProductVersion> filteredProductVersionList) {
        if (product.getProductVersion() > 0L) {
            if(filteredProductVersionList == null || filteredProductVersionList.isEmpty()) {
                productVersionList.add(ProductVersion.forProduct(product));
            }
        }
    }
    
    private void refreshProductAndProductVersionList(List<Product> productList, List<ProductVersion> productVersionList,
            List<Product> filteredProductList) {
        if(filteredProductList != null && !filteredProductList.isEmpty()) {
            productList.removeAll(filteredProductList);
            productVersionList.addAll(ProductVersion.forProductList(filteredProductList));
        }
    }
    
    private void setProductVersion(Product product, List<ProductVersion> filteredProductVersionList) {
        if(filteredProductVersionList != null && !filteredProductVersionList.isEmpty()) {
            Long versionCounter =	filteredProductVersionList.get(filteredProductVersionList.size() - 1).getProductVersion();
            product.setProductVersion(versionCounter + 1L);
        }else {
            product.setProductVersion(product.getProductVersion() + 1L);
        }
    }
    
    private List<ProductVersion> filterProductVersionByProductIdentifier(List<ProductVersion> productVersionList,
            StoreProductRequest p) {
        return  productVersionList.stream().
                filter( s -> s.getProductId().equals(p.getProductIdentifier().getProductId()) && s.getBrand().equalsIgnoreCase(p.getProductIdentifier().getBrand())).collect(Collectors.toList());
    }
    
    private List<Product> filterProductListByProductIdentifier(List<Product> productList, StoreProductRequest p) {
        return productList.stream()
                .filter( o -> o.getProductId().equals(p.getProductIdentifier().getProductId())
                        && o.getBrand().equalsIgnoreCase(p.getProductIdentifier().getBrand())).collect(Collectors.toList());
    }
}
