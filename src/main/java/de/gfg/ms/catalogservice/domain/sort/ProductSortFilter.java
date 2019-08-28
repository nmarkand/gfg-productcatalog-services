package de.gfg.ms.catalogservice.domain.sort;

import java.util.List;

public class ProductSortFilter {
    
    private List<String> sortProperties;
    private String sortOrder;
	private Integer pageSize;
	private Integer pageCount;
    
    public List<String> getSortProperties() {
        return sortProperties;
    }
    
    public void setSortProperties(List<String> sortProperties) {
        this.sortProperties = sortProperties;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "ProductSortFilter [sortProperties=" + sortProperties + ", sortOrder=" + sortOrder + ", pageSize="
				+ pageSize + ", pageCount=" + pageCount + "]";
	}
}
