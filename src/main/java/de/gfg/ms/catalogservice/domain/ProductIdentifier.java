package de.gfg.ms.catalogservice.domain;

public class ProductIdentifier {

	    private Long productId;
	    private String brand;
	    	    
		public ProductIdentifier() {

		}

		public ProductIdentifier(Long productId, String brand) {
			this.productId = productId;
			this.brand = brand;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}		
}
