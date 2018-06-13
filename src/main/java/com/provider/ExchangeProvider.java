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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Component
@Slf4j
public class ExchangeProvider {
    @Value("${cryptocompare.url}")
    private String cryptoCompareUrl;

    @Autowired
    private CacheManager cacheManager;

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

        readFileRates();
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

//    private void readFileRates(){
//        String filepath = "ethUsd.csv";
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
//
//        try {
//            File file = new ClassPathResource(filepath).getFile();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            Map<LocalDate, Double> map = br.lines()
//                    .map(line -> {
//                        String[] tokens = line.split(",");
//                        return new Rate(LocalDate.parse(tokens[0], dtf), Double.valueOf(tokens[1]));
//                    })
//                    .collect(Collectors.toMap(Rate::getLocalDate, Rate::getRate));
//            br.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void readFileRates(){
        String filepath = "ethusd.csv";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");

        try {
            File file = new ClassPathResource(filepath).getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            br.lines().forEach(line-> {
                    String[] tokens = line.split(",");
                    cacheManager.getCache("ethusd").put(LocalDate.parse(tokens[0], dtf), Double.valueOf(tokens[1]));
            });
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AllArgsConstructor
    @Getter
    private class Rate{
        private LocalDate localDate;
        private Double rate;
    }
}
