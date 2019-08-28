package de.gfg.ms.catalogservice;

public class GfgCatalogServiceException extends RuntimeException {
    private static final long serialVersionUID = -3155056304831037735L;
    
    public GfgCatalogServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public GfgCatalogServiceException(String message) {
        super(message);
    }
}

