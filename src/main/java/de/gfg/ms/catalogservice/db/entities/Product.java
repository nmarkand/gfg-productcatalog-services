package de.gfg.ms.catalogservice.db.entities;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.gfg.ms.catalogservice.domain.ProductIdentifier;

@Entity
@Table(name = "TBL_PRODUCT")
public class Product {

	@Id
    @GeneratedValue(generator = "ProductSequence")
    @SequenceGenerator(name = "ProductSequence", sequenceName = "SEQUENCE_TBL_PRODUCT", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "brand", nullable = false)
    private String brand;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @JsonIgnore
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    
    @JsonIgnore
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    @JsonIgnore
    @Column(name="product_version", nullable = false)
    private Long productVersion;
    
    public Product() {
	}

	public Product(ProductIdentifier productIdentifier) {
    	setBrand(productIdentifier.getBrand());
    	setProductId(productIdentifier.getProductId());
    	setProductVersion(0L);
    	setCreatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
    	setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Long getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(Long productVersion) {
		this.productVersion = productVersion;
	}

	public ProductIdentifier getProductIdentifier() {
	        return new ProductIdentifier(getProductId(), getBrand());
	    }

	@Override
	public String toString() {
		return "Product [id=" + id + ", productId=" + productId + ", brand=" + brand + ", title=" + title
				+ ", description=" + description + ", color=" + color + ", price=" + price + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", productVersion=" + productVersion + "]";
	}	
}
