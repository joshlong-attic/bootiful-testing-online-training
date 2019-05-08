package com.example.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationRepositoryTest {

	@Autowired
	private ReservationRepository repository;

	@Test
	public void query() throws Exception {


		Flux<Reservation> flux = this.repository
			.deleteAll()
			.thenMany(Flux
				.just("A", "B", "C", "C")
				.map(name -> new Reservation(null, name))
				.flatMap(r -> this.repository.save(r))
			)
			.thenMany(this.repository.findByName("C"));

		StepVerifier
			.create(flux)
			.expectNextCount(2)
			.verifyComplete();

	}
}
