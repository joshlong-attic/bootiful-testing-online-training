package com.example.producer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationEntityTest {

	@Autowired
	private ReactiveMongoTemplate template;

	@Test
	public void persist() throws Exception {

		Mono<Reservation> name = this.template.save(new Reservation(null, "Name"));

		StepVerifier
			.create(name)
			.expectNextMatches(reservation -> reservation.getName().equalsIgnoreCase("name") &&
				!StringUtils.isEmpty(reservation.getId()))
			.verifyComplete();

	}
}
