package com.example.customerclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.springframework.http.HttpMethod.GET;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class CustomerClient {

	private final RestTemplate restTemplate;
	private final String uri;

	public CustomerClient(RestTemplate restTemplate, String uri) {
		this.restTemplate = restTemplate;
		this.uri = uri;
	}

	public Collection<Customer> getCustomers() {

		ParameterizedTypeReference<Collection<Customer>> ptr =
			new ParameterizedTypeReference<Collection<Customer>>() { };

		return restTemplate.exchange(this.uri + "/customers", GET, null, ptr)
			.getBody();
	}

	public Customer getCustomerById(Long id) {
		return restTemplate.exchange(this.uri + "/customers/{id}", GET, null, Customer.class, id)
			.getBody();
	}
}
