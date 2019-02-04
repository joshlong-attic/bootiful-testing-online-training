package com.example.mrss;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest
@RunWith(SpringRunner.class)
public class GreetingsClientTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private GreetingsClient greetingsClient;

	@Test
	public void greet() throws Exception {

		String yoJosh = "yo josh";
		String json = this.objectMapper.writeValueAsString(new Greeting(yoJosh));

		RestGatewaySupport restGatewaySupport = new RestGatewaySupport();
		restGatewaySupport.setRestTemplate(this.restTemplate);

		MockRestServiceServer server = MockRestServiceServer.createServer(this.restTemplate);

		server
			.expect(once(), requestTo("http://localhost:8080/greetings/josh"))
			.andRespond(withSuccess(json, MediaType.APPLICATION_JSON_UTF8));

		Greeting yoToJosh = this.greetingsClient.greet("josh");
		Assert.assertEquals(yoToJosh.getGreeting(), yoJosh);

		server.verify();
	}


}