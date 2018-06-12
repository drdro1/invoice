package com.model.report;

import com.model.ethereum.EthereumTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class DailyReport {
    private List<EthereumTransaction> transactionList;
    private BigInteger totalBalance;
    private BigInteger dayBalance;
}
