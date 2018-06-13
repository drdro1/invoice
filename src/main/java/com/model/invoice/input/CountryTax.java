package com.model.invoice.input;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
public enum CountryTax {
    france(0.196);

    private double rate;

    private CountryTax(double rate){
        this.rate = rate;
    }
}
