package com.example.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebApplication {

	private final GreetingService greetingService;

	WebApplication(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GetMapping("/greet/{name}")
	Greeting greet(@PathVariable String name) {
		return this.greetingService.greet(name);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}

@Service
class GreetingService {

	Greeting greet(String name) {
		return new Greeting("hello, " + name + "!");
	}

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Greeting {
	private String greeting;
}