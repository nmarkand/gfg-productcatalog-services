package de.gfg.ms.catalogservice.domain;

import java.util.List;

public class StoreProductListResponse {
    List<Long> dbIdList;
    
    public StoreProductListResponse() {
        
    }
    
    public StoreProductListResponse(List<Long> dbIdList) {
        this.dbIdList = dbIdList;
    }
    
    public List<Long> getDbIdList() {
        return dbIdList;
    }
    
    public void setDbIdList(List<Long> dbIdList) {
        this.dbIdList = dbIdList;
    }
}
