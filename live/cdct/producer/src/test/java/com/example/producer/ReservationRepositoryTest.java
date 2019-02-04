package com.example.producer;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationRepositoryTest {

	private final Reservation one = new Reservation(null, "Francois");
	private final Reservation two = new Reservation(null, "Marie-Jeanne");

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	public void findByName() throws Exception {

		Flux<Reservation> saved = Flux.just(this.one, this.two).flatMap(this.reservationRepository::save);
		StepVerifier
			.create(saved
				.thenMany(this.reservationRepository.findByReservationName(this.one.getReservationName()))
			)
			.expectNextMatches(r -> r.getReservationName().equalsIgnoreCase(this.one.getReservationName()))
			.verifyComplete();


	}

	@After
	public void after() {
		StepVerifier
			.create(this.reservationRepository.deleteAll())
			.verifyComplete();
	}
}
