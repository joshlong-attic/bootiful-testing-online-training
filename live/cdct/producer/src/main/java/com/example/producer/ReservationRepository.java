package com.example.producer;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, String> {

	// @Query
	Flux<Reservation> findByName(String name);
}
