package com.simplebojocs.utils;

/**
 * AVISO: Si no se utiliza como enum (esta interfaz o cualquiera que la implemente)
 * es muy posible que se tenga que hacer @Override de equals() y hashCode()
 */
public interface iResource {
    enum ResourceType{
        INTERNAL
    }
    String getFile();
    String getPath();
    ResourceType getResourceType();
}
