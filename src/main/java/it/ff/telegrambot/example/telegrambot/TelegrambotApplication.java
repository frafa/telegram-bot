package it.ff.telegrambot.example.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TelegrambotApplication {

	public static void main(String[] args) {

		//Initialize bot context
		ApiContextInitializer.init();
		
		SpringApplication.run(TelegrambotApplication.class, args);
	}

}

