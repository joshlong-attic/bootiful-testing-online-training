package com.example.reservationclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}

	@Bean
	WebClient client(WebClient.Builder builder) {
		return builder.build();
	}
}
