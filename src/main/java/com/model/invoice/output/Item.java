package com.model.invoice.output;

import com.model.invoice.Period;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
@Data
@AllArgsConstructor
public class Item {
    private String currency;
    private String address;
    private Period period;
    private InvoicePrice average_position;
}
