package de.gfg.ms.catalogservice.db.entities;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import de.gfg.ms.catalogservice.domain.ProductIdentifier;

@Entity
@Table(name = "TBL_PRODUCT_VERSION")
public class ProductVersion {
	
    @Id
    @GeneratedValue(generator = "ProductVersionSequence")
    @SequenceGenerator(name = "ProductVersionSequence", sequenceName = "SEQUENCE_TBL_PRODUCT_VERSION", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "brand")
    private String brand;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "valid_from")
    private ZonedDateTime validFrom;
    
    @Column(name = "valid_to")
    private ZonedDateTime validTo;
    
    @Column(name = "product_version")
    private Long productVersion;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ZonedDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(ZonedDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public ZonedDateTime getValidTo() {
		return validTo;
	}

	public void setValidTo(ZonedDateTime validTo) {
		this.validTo = validTo;
	}
	
	public Long getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(Long productVersion) {
		this.productVersion = productVersion;
	}

	public ProductVersion() {       
    }
	
	public ProductVersion(ProductIdentifier productIdentifier) {
		setBrand(productIdentifier.getBrand());
        setProductId(productIdentifier.getProductId());
        setValidTo(ZonedDateTime.now(ZoneId.of("UTC")));
	}

	public static ProductVersion forProduct(final Product product) {
		ProductVersion version = new ProductVersion(product.getProductIdentifier());
        
        ZonedDateTime validFrom = product.getUpdatedAt() != null ? product.getUpdatedAt() : product.getCreatedAt();
        version.setValidFrom(validFrom);
        version.setColor(product.getColor());
        version.setDescription(product.getDescription());
        version.setPrice(product.getPrice());
        version.setTitle(product.getTitle());
        version.setProductVersion(product.getProductVersion());
        return version;
    }
    
	public static List<ProductVersion> forProductList(final List<Product> productList) {
		List<ProductVersion> versionList = new ArrayList<>();
		productList.stream().forEachOrdered(q -> {
			ProductVersion version = new ProductVersion(q.getProductIdentifier());
	        
	        ZonedDateTime validFrom = q.getUpdatedAt() != null ? q.getUpdatedAt() : q.getCreatedAt();
	        version.setValidFrom(validFrom);
	        version.setColor(q.getColor());
	        version.setDescription(q.getDescription());
	        version.setPrice(q.getPrice());
	        version.setTitle(q.getTitle());
	        version.setProductVersion(q.getProductVersion());
	        versionList.add(version);
		});
		
        return versionList;
    }
	
	@Override
	public String toString() {
		return "ProductVersion [id=" + id + ", productId=" + productId + ", title=" + title + ", brand=" + brand
				+ ", description=" + description + ", color=" + color + ", price=" + price + ", validFrom=" + validFrom
				+ ", validTo=" + validTo + ", productVersion=" + productVersion + "]";
	}    
}
