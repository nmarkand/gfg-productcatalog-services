package de.gfg.ms.catalogservice.db.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.gfg.ms.catalogservice.db.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    public Product findProductByProductIdAndBrand(final Long productId, final String brand);
    
    public List<Product> findProductListByTitleAndDescriptionContainingIgnoreCase(String title, String description);
}

