package com.example.producer;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationDocumentTest {

	private final Reservation one = new Reservation(null, "Mario");
	private final Reservation two = new Reservation(null, "Jane");

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Test
	public void persist() throws Exception {

		Flux<Reservation> namedItems = Flux.just(this.one, this.two);
		Flux<Reservation> flux = namedItems.flatMap(this.reactiveMongoTemplate::save);

		Predicate<Reservation> reservationPredicate =
			reservation -> StringUtils.hasText(reservation.getId()) &&
				StringUtils.hasText(reservation.getReservationName());

		StepVerifier
			.create(flux)
			.expectNextMatches(reservationPredicate)
			.expectNextMatches(reservationPredicate)
			.verifyComplete();

	}

}
