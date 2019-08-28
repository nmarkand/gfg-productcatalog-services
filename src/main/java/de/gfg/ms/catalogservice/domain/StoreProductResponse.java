package de.gfg.ms.catalogservice.domain;

public class StoreProductResponse {
	
	private Long dbId;
	
	public Long getDbId() {
		return dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	@Override
	public String toString() {
		return "StoreProductResponse [dbId=" + dbId + "]";
	}
}
