package com.model;

import com.model.Ethereum.EthereumTransaction;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Setter
@NoArgsConstructor
public class DailyReport {
    private List<EthereumTransaction> transactionList;
    private BigInteger totalBalance;
    private BigInteger dayBalance;
}
