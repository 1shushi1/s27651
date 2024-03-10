package com.example.s27651;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlashCardsApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FlashCardsApp.class, args);
		FlashCardsController flashCardsController = context.getBean(FlashCardsController.class);
		flashCardsController.start();
	}
}
