package com.example.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@AutoConfigureWireMock(port = 8080)
@SpringBootTest
@RunWith(SpringRunner.class)
public class GreetingsClientTest {

	@Autowired
	private GreetingsClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void greet() throws Exception {
		String greeting = "hello, world";
		String json = this.objectMapper.writeValueAsString(new Greeting(greeting));
		WireMock
			.stubFor(
				get(urlEqualTo("/greetings/world")).willReturn(
					aResponse()
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBody(json)
						.withStatus(HttpStatus.OK.value())
				));
		Greeting greet = this.client.greet("world");
		Assert.assertEquals(greet.getGreeting(), greeting);
	}

}