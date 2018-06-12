package com.client;

import feign.Param;
import feign.RequestLine;

import java.util.HashMap;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public interface CryptoCompareClient {
    @RequestLine("GET /data/pricehistorical?fsym={fsym}&tsyms={tsyms}&ts={ts}")
    HashMap<String, HashMap<String, Double>> convert(@Param("fsym")String fsym,
                     @Param("tsyms")String tsyms,
                     @Param("ts")long timestamp);
}
