package com.client;

import com.model.ethereum.EthereumTransactionsMessage;
import feign.Param;
import feign.RequestLine;

/**
 * Created by alejandrosantamaria on 09/06/18.
 */
public interface EtherscanClient {
    @RequestLine("GET /api?module={module}" +
            "&action={action}" +
            "&address={address}" +
            "&startblock={startblock}" +
            "&endblock={endblock}" +
            "&sort={sort}" +
            "&apikey={apikey}")
    EthereumTransactionsMessage getTransactions(
                                @Param("module") String module,
                                @Param("action") String action,
                                @Param("address") String address,
                                @Param("startblock") Integer startblock,
                                @Param("endblock") Integer endblock,
                                @Param("sort") String sort,
                                @Param("apikey") String apikey);
}
