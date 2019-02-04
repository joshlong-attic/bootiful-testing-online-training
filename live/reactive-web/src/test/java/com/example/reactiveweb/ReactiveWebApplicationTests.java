package com.example.reactiveweb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest
public class ReactiveWebApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void contextLoads() {

		this.webTestClient
			.get()
			.uri("http://locahost:8080/greetings")
			.exchange()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			.expectStatus().isOk()
			//.expectBody().jsonPath("@.greeting").isEqualTo("hello, world")
			.expectBody(Greeting.class)
			.value(greeting -> Assert.assertEquals(greeting.getGreeting(), "hello, world"));

	}

}

