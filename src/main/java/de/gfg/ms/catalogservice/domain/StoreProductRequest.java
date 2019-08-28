package de.gfg.ms.catalogservice.domain;

import java.math.BigDecimal;

public class StoreProductRequest {

	private ProductIdentifier productIdentifier;	
	private String description;
	private String color;
	private BigDecimal price;
	private String title;
	
	public StoreProductRequest() {
	}
	
	public StoreProductRequest(ProductIdentifier productIdentifier, String description, String color, BigDecimal price,
			String title) {
		this.productIdentifier = productIdentifier;
		this.description = description;
		this.color = color;
		this.price = price;
		this.title = title;
	}

	public ProductIdentifier getProductIdentifier() {
		return productIdentifier;
	}

	public void setProductIdentifier(ProductIdentifier productIdentifier) {
		this.productIdentifier = productIdentifier;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
}
