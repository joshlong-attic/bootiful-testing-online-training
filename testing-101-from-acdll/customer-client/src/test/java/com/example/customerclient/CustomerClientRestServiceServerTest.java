package com.example.customerclient;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
public class CustomerClientRestServiceServerTest {

	private Resource customers = new ClassPathResource("customers.json");
	private Resource customerById = new ClassPathResource("customer-by-id.json");
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private CustomerClient client;

	@Autowired
	private RestTemplate restTemplate;

	@Before
	public void before() {
		this.mockRestServiceServer = MockRestServiceServer.bindTo(this.restTemplate)
			.build();
	}

	@Test
	public void customersShouldReturnAllCustomers() {

		this.mockRestServiceServer
			.expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(this.customers, MediaType.APPLICATION_JSON_UTF8));

		Collection<Customer> customers = client.getCustomers();
		BDDAssertions.then(customers.size()).isEqualTo(2);

		this.mockRestServiceServer.verify();
	}

	@Test
	public void customerByIdShouldReturnACustomer() {

		this.mockRestServiceServer
			.expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers/1"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(this.customerById, MediaType.APPLICATION_JSON_UTF8));

		Customer customer = client.getCustomerById(1L);
		BDDAssertions.then(customer.getFirstName()).isEqualToIgnoringCase("first");
		BDDAssertions.then(customer.getLastName()).isEqualToIgnoringCase("last");
		BDDAssertions.then(customer.getEmail()).isEqualToIgnoringCase("email");
		BDDAssertions.then(customer.getId()).isEqualTo(1L);
		this.mockRestServiceServer.verify();
	}
}
