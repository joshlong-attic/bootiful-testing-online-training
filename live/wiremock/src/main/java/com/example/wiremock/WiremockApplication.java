package com.example.wiremock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WiremockApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiremockApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Greeting {
	private String greeting;

}

@Component
class GreetingsClient {

	private final RestTemplate restTemplate;

	GreetingsClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Greeting greet(String name) {
		return this.restTemplate
			.getForEntity("http://localhost:8080/greetings/{name}", Greeting.class, name)
			.getBody();

	}
}