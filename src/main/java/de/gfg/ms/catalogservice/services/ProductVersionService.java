package de.gfg.ms.catalogservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.gfg.ms.catalogservice.db.entities.ProductVersion;
import de.gfg.ms.catalogservice.db.repos.ProductVersionRepository;
import de.gfg.ms.catalogservice.domain.ProductIdentifier;

@Service
public class ProductVersionService {
    ProductVersionRepository productVersionRepository;
    
    @Autowired
    public ProductVersionService(ProductVersionRepository productVersionRepository) {
        this.productVersionRepository = productVersionRepository;
    }
    
    public ProductVersion saveProductVersion(final ProductVersion productVersion) {
        return productVersionRepository.save(productVersion);
    }
    
    public List<ProductVersion> saveProductVersionList(final List<ProductVersion> productVersionList) {
        return productVersionRepository.saveAll(productVersionList);
    }
    
    public List<ProductVersion> getProductVersions(final ProductIdentifier productIdentifier) {
        return productVersionRepository.findProductVersionListByProductIdAndBrand(productIdentifier.getProductId(), productIdentifier.getBrand());
    }
}