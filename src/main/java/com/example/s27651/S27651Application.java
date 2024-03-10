package com.example.s27651;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class S27651Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(S27651Application.class, args);
		FlashCardsController flashCardsController = context.getBean(FlashCardsController.class);
		flashCardsController.start();
	}
}
