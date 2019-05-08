package com.example.producer;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class ReservationPojoTest {

	@Test
	public void create() {
		Reservation re = new Reservation("1", "Name");
		Assert.assertEquals(re.getId(), "1");
		Assert.assertEquals(re.getName(), "Name");
		Assertions.assertThat(re.getName()).isEqualToIgnoringCase("name");
	}
}
