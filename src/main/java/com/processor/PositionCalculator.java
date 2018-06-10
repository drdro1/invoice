package com.processor;

import com.model.Ethereum.EthereumTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
public class PositionCalculator {

    public double calculate(List<EthereumTransaction> transactionList, LocalDate beginDate, LocalDate endDate){
        transactionList.stream()
                .collect(Collectors.groupingBy(ethereumTransaction -> {
                    return ethereumTransaction.getBlockHash();
                }));

        return 0.0;
    }


}
