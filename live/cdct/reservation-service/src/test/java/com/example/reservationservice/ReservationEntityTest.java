package com.example.reservationservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationEntityTest {

	@Autowired
	private ReservationRepository repository;

	@Test
	public void persist() throws Exception {
		Flux<Reservation> just = Flux.just(new Reservation(null, "Jane"), new Reservation(null, "Joe"));
		Flux<Reservation> reservationFlux = this.repository.saveAll(just);
		StepVerifier
			.create(reservationFlux)
			.expectNextCount(1)
			.expectNextMatches(res -> res.getName().equalsIgnoreCase("Joe") && StringUtils.hasText(res.getId()))
			.verifyComplete();
	}

}
