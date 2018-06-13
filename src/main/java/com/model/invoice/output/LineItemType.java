package com.model.invoice.output;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
public enum LineItemType {
    FLAT_FEE("flat_fee"),
    SERVICE_FEE("service_fee");

    private String name;

    LineItemType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
