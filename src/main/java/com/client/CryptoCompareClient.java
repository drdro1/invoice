package com.client;

import com.model.Exchange;
import feign.Param;
import feign.RequestLine;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
public interface CryptoCompareClient {
    @RequestLine("GET /data/pricehistorical?fsym={fsym}&tsyms={tsyms}&ts={ts}")
    Exchange convert(@Param("fsym")String fsym,
                     @Param("tsyms")String tsyms,
                     @Param("ts")long timestamp);
}
