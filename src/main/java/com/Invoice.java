package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Invoice {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Invoice.class);

		System.out.println("Starting Invoice app...");
		ApplicationContext ctx = SpringApplication.run(Invoice.class, args);

	}
}
