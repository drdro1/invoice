package com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Invoice{
	public static void main(String[] args) {
		System.out.println("Starting Invoice app...");
		SpringApplication.run(Invoice.class, args);
	}


}