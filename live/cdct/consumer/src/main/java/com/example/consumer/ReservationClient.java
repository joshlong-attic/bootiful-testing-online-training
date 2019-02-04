package com.example.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Component
public class ReservationClient {

	private final WebClient webClient;

	public ReservationClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public Flux<Reservation> getAllReservations() throws Exception {
		return this.webClient
			.get()
			.uri("http://localhost:8080/reservations")
			.retrieve()
			.bodyToFlux(Reservation.class)
			;
	}
}
