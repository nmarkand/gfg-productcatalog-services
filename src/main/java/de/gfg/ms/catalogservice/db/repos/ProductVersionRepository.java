package de.gfg.ms.catalogservice.db.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.gfg.ms.catalogservice.db.entities.ProductVersion;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
    
    public List<ProductVersion> findProductVersionListByProductIdAndBrand(final Long productId,
            final String brand);
    
    public ProductVersion findProductVersionByProductIdAndBrandAndProductVersion(final Long productId,
            final String brand, final Long productVersion);
    
}