package com.model.report;

import lombok.Data;

import java.util.List;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@Data
public class InvoiceQuery {
    private Period period;
    private List<Account> accounts;
    private CountryTax tax;
}