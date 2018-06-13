package com.model.invoice.input;

import lombok.Data;

import java.util.List;

/**
 * Created by alejandrosantamaria on 12/06/18.
 */
@Data
public class Account {
    private String name;
    private String currency;
    private List<String> addresses;
}
