package com.example.producer;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class ReservationPojoTest {

	@Test
	public void create() throws Exception {

		Reservation reservation = new Reservation("1", "Jane");
		Assert.assertEquals(reservation.getId(), "1");
		Assert.assertEquals(reservation.getReservationName(), "Jane");

		Assert.assertThat(reservation.getId(), Matchers.equalTo("1"));
		Assert.assertThat(reservation.getReservationName(), Matchers.equalTo("Jane"));

		Assertions.assertThat(reservation.getReservationName()).isEqualToIgnoringCase("Jane");
		Assertions.assertThat(reservation.getId()).isEqualToIgnoringCase("1");
	}
}
