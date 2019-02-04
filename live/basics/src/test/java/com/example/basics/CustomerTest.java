package com.example.basics;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class CustomerTest {

	@Test
	public void assertEqualityOfFieldsSetInConstructor() {
		Customer customer = new Customer("F", "L");
		Assert.assertEquals(customer.getFirst(), "F");
		Assert.assertEquals(customer.getLast(), "L");
		Assert.assertThat(customer.getFirst(), Matchers.is("F"));
		Assert.assertThat(customer.getLast(), Matchers.is("L"));
		Assertions.assertThat(customer.getFirst()).isEqualTo("F");
		Assertions.assertThat(customer.getLast()).isEqualTo("L");
	}
}