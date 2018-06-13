package com.model.invoice.output;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
public enum InvoiceCurrency {
    USD("USD"),
    ETH("ethereum");

    private String name;

    InvoiceCurrency(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
