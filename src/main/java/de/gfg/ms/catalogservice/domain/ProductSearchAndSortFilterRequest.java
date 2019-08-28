package de.gfg.ms.catalogservice.domain;

import de.gfg.ms.catalogservice.domain.sort.ProductSortFilter;

public class ProductSearchAndSortFilterRequest {
	
	private ProductSearchFilter productSearchFilter;
	private ProductSortFilter productSortFilter;
	
	public ProductSearchFilter getProductSearchFilter() {
		return productSearchFilter;
	}
	
	public void setProductSearchFilter(ProductSearchFilter productSearchFilter) {
		this.productSearchFilter = productSearchFilter;
	}
	
	public ProductSortFilter getProductSortFilter() {
		return productSortFilter;
	}
	
	public void setProductSortFilter(ProductSortFilter productSortFilter) {
		this.productSortFilter = productSortFilter;
	}

	@Override
	public String toString() {
		return "ProductSearchAndSortFilterRequest [productSearchFilter=" + productSearchFilter + ", productSortFilter="
				+ productSortFilter + "]";
	}
}
