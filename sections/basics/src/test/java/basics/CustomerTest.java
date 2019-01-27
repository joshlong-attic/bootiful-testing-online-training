package basics;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;


public class CustomerTest {

	@Test
	public void createWithConstructorWithParams() throws Exception {

		Customer customer = new Customer("id", "name");

		Assert.assertEquals(customer.getId(), "id");
		Assert.assertNotNull(customer.getId());

		Assert.assertThat(customer.getName(), Matchers.not(Matchers.isEmptyString()));
		Assert.assertThat(customer.getId(), Matchers.is("id"));

		Assertions.assertThat(customer.getId()).isNotBlank();
		Assertions.assertThat(customer.getId()).isEqualTo("id");

	}
}
