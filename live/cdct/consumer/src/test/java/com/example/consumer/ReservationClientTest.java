package com.example.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static org.springframework.util.StringUtils.hasText;

@SpringBootTest
//@AutoConfigureWireMock(port = 8080)
@AutoConfigureStubRunner(
	ids = "com.example:producer:+:8080",
	stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@AutoConfigureJson
@RunWith(SpringRunner.class)
public class ReservationClientTest {

	@Autowired
	private ReservationClient client;

//	@Autowired
//	private ObjectMapper objectMapper;

	@Test
	public void getAllReservations() throws Exception {

	/*
	Collection<Reservation> re = Arrays.asList(
			new Reservation("1", "Jane"),
			new Reservation("2", "Joe"));

		String json = objectMapper.writeValueAsString(re);

		stubFor(
			get(urlEqualTo("/reservations"))
				.willReturn(aResponse()
					.withBody(json)
					.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.withStatus(HttpStatus.OK.value())
				)
		);
*/

		Flux<Reservation> allReservations = this.client.getAllReservations();

		StepVerifier
			.create(allReservations)
			.expectNextMatches(predicate("1", "Jane"))
			.expectNextMatches(predicate("2", "Joe"))
			.verifyComplete();
	}

	Predicate<Reservation> predicate(String id, String name) {
		return reservation -> hasText(reservation.getName())
			&& reservation.getName().equalsIgnoreCase(name)
			&& hasText(reservation.getId())
			&& reservation.getId().equalsIgnoreCase(id);
	}


}
