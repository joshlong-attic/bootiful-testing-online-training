package com.example.customerclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.Collection;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8080)
public class CustomerClientWiremockTest {

	private final Resource customers = new ClassPathResource("customers.json");
	private final Resource customerById = new ClassPathResource("customer-by-id.json");

	@Autowired
	private CustomerClient client;

	@Test
	public void customersShouldReturnAllCustomers() {

		WireMock.stubFor(WireMock.get(WireMock.urlMatching("/customers"))
			.willReturn(WireMock.aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.withStatus(HttpStatus.OK.value())
				.withBody(asJson(customers))));

		Collection<Customer> customers = client.getCustomers();
		BDDAssertions.then(customers.size()).isEqualTo(2);
	}

	@SneakyThrows
	private String asJson(Resource resource) {
		return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
	}

	@Test
	public void customerByIdShouldReturnACustomer() {

		WireMock.stubFor(WireMock.get(WireMock.urlMatching("/customers/1"))
			.willReturn(
				WireMock.aResponse()
					.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.withStatus(HttpStatus.OK.value())
					.withBody(asJson(customerById))
			));

		Customer customer = client.getCustomerById(1L);
		BDDAssertions.then(customer.getFirstName()).isEqualToIgnoringCase("first");
		BDDAssertions.then(customer.getLastName()).isEqualToIgnoringCase("last");
		BDDAssertions.then(customer.getEmail()).isEqualToIgnoringCase("email");
		BDDAssertions.then(customer.getId()).isEqualTo(1L);
	}
}
