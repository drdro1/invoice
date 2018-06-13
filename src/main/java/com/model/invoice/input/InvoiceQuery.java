package com.model.invoice.input;

import com.model.invoice.Period;
import lombok.Data;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@Data
public class InvoiceQuery {
    private Period period;
    private Account account;
    private CountryTax tax;
}