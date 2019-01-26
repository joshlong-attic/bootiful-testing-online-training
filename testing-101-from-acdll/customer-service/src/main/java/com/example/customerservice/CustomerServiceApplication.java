package com.example.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

	private final CustomerRepository customerRepository;

	CustomerServiceApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void go() {
		Stream
			.of("a", "b", "c")
			.forEach(n -> customerRepository.save(new Customer(null, n, n, n + "@" + n + ".com")));
	}
}
