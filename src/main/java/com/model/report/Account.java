package com.model.report;

import lombok.Data;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@Data
public class Account {
    private String name;
    private String currency;
    private String[] addresses;
}
