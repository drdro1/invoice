package com;

import com.provider.EthereumTransactionProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class Invoice implements CommandLineRunner {

	@Autowired
	private EthereumTransactionProvider ethereumTransactionProvider;

	@Override
	public void run(String... strings) throws Exception {

	}


	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Invoice.class);

		System.out.println("Starting Invoice app...");
		ApplicationContext ctx = SpringApplication.run(Invoice.class, args);

	}


}
