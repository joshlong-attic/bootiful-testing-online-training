package com.example.reservationservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest
@Import(ReservationHttpConfig.class)
@RunWith(SpringRunner.class)
class ReservationHttpTest {

	@MockBean
	private ReservationRepository reservationRepository;

	@Autowired
	private WebTestClient client;

	@Test
	public void getAllReservations() {

		Mockito
			.when(this.reservationRepository.findAll())
			.thenReturn(Flux.just(new Reservation("1", "Jane"), new Reservation("2", "Joe")));

		this.client
			.get()
			.uri("http://localhost:8080/reservations")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody().jsonPath("@.[0].name").isEqualTo("Jane");
	}
}
