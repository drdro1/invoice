package com.model.ethereum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class EthereumTransactionsMessage {
    private Integer status;
    private String message;
    private List<EthereumTransaction> result;
}
