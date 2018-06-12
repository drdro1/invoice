package com.provider;

import com.client.EtherscanClient;
import com.model.ethereum.EthereumTransactionsMessage;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Component
@Slf4j
public class EthereumTransactionProvider {

    @Value("${etherscan.url}")
    private String etherscanUrl;

    private EtherscanClient etherscanClient;

    @PostConstruct
    private void init(){
        this.etherscanClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(EtherscanClient.class))
                .logLevel(Logger.Level.FULL)
                .target(EtherscanClient.class, etherscanUrl);
    }

    @Cacheable(value = "eththx", key = "#address")
    public EthereumTransactionsMessage getEtherscanTransactions(String address){
        log.info("Calling etherscan transactions with address:{}", address);

        EthereumTransactionsMessage ethereumTransactionsMessage = etherscanClient.getTransactions("account",
                "txlist",
                address,
                0,
                99999999,
                "asc",
                "YourApiKeyToken");

        return ethereumTransactionsMessage;
    }
}
