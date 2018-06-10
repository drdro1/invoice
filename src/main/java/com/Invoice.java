package com;

import com.model.Ethereum.EthereumTransaction;
import com.model.Ethereum.EthereumTransactionsMessage;
import com.model.Exchange;
import com.processor.PositionCalculator;
import com.provider.EthereumTransactionProvider;
import com.provider.ExchangeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class Invoice implements CommandLineRunner {

	@Autowired
	private EthereumTransactionProvider ethereumTransactionProvider;

	@Autowired
	private PositionCalculator positionCalculator;

	@Autowired
	private ExchangeProvider exchangeProvider;

	@Override
	public void run(String... strings) throws Exception {
		testCryptoCompare();
	}

	private void testCryptoCompare(){
		Exchange exchange = exchangeProvider.getEtherUsd(LocalDate.of(2018, 6, 5));
		log.info("ethusd = {}", exchange);
	}

	private void testStream(){
		long millis = System.currentTimeMillis();
		EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions("0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae");
		log.info("Time={}", (System.currentTimeMillis() - millis));

		Map<LocalDate, List<EthereumTransaction>> mapListDailyTransactions =
				positionCalculator.getMapListDailyTransactions(ethTxMsg.getResult());

	}

	private void testCache() throws Exception {
		long millis = System.currentTimeMillis();
		EthereumTransactionsMessage ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions("0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae");
		log.info("Time={}", (System.currentTimeMillis()-millis));
		Thread.sleep(1000);

		millis = System.currentTimeMillis();
		ethTxMsg = ethereumTransactionProvider.getEtherscanTransactions("0xede0198e4fb0959fab7521b6899abce999fd7dcb");
		log.info("Time={}", (System.currentTimeMillis()-millis));
	}

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Invoice.class);

		System.out.println("Starting Invoice app...");
		ApplicationContext ctx = SpringApplication.run(Invoice.class, args);

	}


}
