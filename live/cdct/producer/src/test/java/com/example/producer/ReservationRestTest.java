package com.example.producer;

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

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@WebFluxTest
@Import(ReservationRestConfiguration.class)
public class ReservationRestTest {

	@MockBean
	private ReservationRepository reservationRepository;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void getAllReservations() throws Exception {

		Mockito.when(this.reservationRepository.findAll())
			.thenReturn(Flux.just(new Reservation("1", "Elsa"), new Reservation("2", "Pablo")));

		this.webTestClient
			.get()
			.uri("/reservations")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("@.[0].reservationName").isEqualTo("Elsa")
			.jsonPath("@.[0].id").isEqualTo("1");
	}

}
