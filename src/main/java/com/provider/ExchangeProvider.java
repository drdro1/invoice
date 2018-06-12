package com.provider;

import com.client.CryptoCompareClient;
import com.client.EtherscanClient;
import com.utils.DateTimeUtils;
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
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Component
@Slf4j
public class ExchangeProvider {
    @Value("${cryptocompare.url}")
    private String cryptoCompareUrl;

    private CryptoCompareClient cryptoCompareClient;

    @PostConstruct
    private void init(){
        this.cryptoCompareClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(EtherscanClient.class))
                .logLevel(Logger.Level.FULL)
                .target(CryptoCompareClient.class, cryptoCompareUrl);
    }

    @Cacheable(value = "ethusd", key = "#localDate")
    public Double getEtherUsd(LocalDate localDate) {
        Double toRet = null;

        HashMap<String, HashMap<String, Double>> mapResult = cryptoCompareClient.convert(
                "ETH", "USD", DateTimeUtils.localDateToTimestamp(localDate));

        if ( mapResult != null ){
            HashMap<String, Double> mapEth = mapResult.get("ETH");
            if ( mapEth != null )
             toRet = mapEth.get("USD");
        }

        return toRet;
    }
}
