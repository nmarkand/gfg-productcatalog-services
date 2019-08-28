package de.gfg.ms.catalogservice.domain;

public class DeleteProductResult {

	private boolean deleted = false;
	
	public DeleteProductResult() {
	}

	public DeleteProductResult(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}	
}
