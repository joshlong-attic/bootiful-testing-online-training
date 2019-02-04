package com.example.producer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public interface ReservationRepository extends ReactiveMongoRepository<Reservation, String > {

	Flux<Reservation> findByReservationName (String rn);
}
