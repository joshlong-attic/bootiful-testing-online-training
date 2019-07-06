package com.example.reservationclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureStubRunner(
	ids = "com.example:reservation-service:+:8080",
	stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@RunWith(SpringRunner.class)
//@AutoConfigureWireMock(port = 8080)
public class ReservationClientApplicationTests {

	@Autowired
	private ReservationClient client;

	//	@Before
	public void before() {

		WireMock
			.stubFor(WireMock.get(WireMock.urlEqualTo("/reservations"))
				.willReturn(WireMock.aResponse()
					.withStatus(HttpStatus.OK.value())
					.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.withBody("[ {\"id\":\"1\" ,\"name\":\"Jane\"},  {\"id\":\"2\" ,\"name\":\"Joe\"} ] ")
				)
			);

	}

	@Test
	public void contextLoads() {

		Flux<Reservation> allReservations = this.client.getAllReservations();
		StepVerifier
			.create(allReservations)
			.expectNextCount(1)
			.expectNextMatches(r -> r.getName().equalsIgnoreCase("Joe") && StringUtils.hasText(r.getName()))
			.verifyComplete();
	}

}
