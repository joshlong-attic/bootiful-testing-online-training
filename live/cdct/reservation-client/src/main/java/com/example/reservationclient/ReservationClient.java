package com.example.reservationclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ReservationClient {

	private final WebClient client;

	ReservationClient(WebClient client) {
		this.client = client;
	}

	public Flux<Reservation> getAllReservations() {
		return this.client
			.get()
			.uri("http://localhost:8080/reservations")
			.retrieve()
			.bodyToFlux(Reservation.class);
	}
}
