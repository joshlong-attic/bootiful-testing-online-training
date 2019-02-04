package com.example.customerservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CustomerRestController {

	private final CustomerRepository customerRepository;

	public CustomerRestController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Customer customerById(@PathVariable Long id) {
		return this.customerRepository
			.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException("couldn't find the record by its ID # " + id + "."));
	}

	@GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<Customer> customers() {
		return this.customerRepository.findAll();
	}
}

class CustomerNotFoundException extends RuntimeException {
	public CustomerNotFoundException(String message) {
		super(message);
	}
}