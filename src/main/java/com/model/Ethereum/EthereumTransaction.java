package com.model.ethereum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class EthereumTransaction {
    private Long blockNumber;
    private Long timeStamp;
    private String hash;
    private String nonce;
    private String blockHash;
    private Long transactionIndex;
    private String from;
    private String to;
    private BigInteger value;
    private Long gas;
    private Long gasPrice;
    private Integer isError;
    private Integer txreceiptStatus;
    private String input;
    private String contractAddress;
    private Long cumulativeGasUsed;
    private Long gasUsed;
    private Integer confirmations;
}
