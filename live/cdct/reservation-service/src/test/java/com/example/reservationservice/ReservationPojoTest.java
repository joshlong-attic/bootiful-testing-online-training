package com.example.reservationservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ReservationPojoTest {

	private final Reservation reservation = new Reservation("1", "Jane");

	@Test
	public void create() throws Exception {

		Assertions.assertEquals(this.reservation.getName(), "Jane");
		Assertions.assertEquals(this.reservation.getId(), "1");

	}
}
