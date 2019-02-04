package com.example.customerservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
	* @author <a href="josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {


	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private CustomerRepository repository;

	@Test
	public void saveShouldMapCorrectly() throws Exception {
		Customer customer = new Customer(null, "first", "last", "email@email.com");
		Customer saved = this.testEntityManager.persistFlushFind(customer);

		BDDAssertions.then(saved.getId()).isNotNull();

		BDDAssertions.then(saved.getFirstName()).isEqualToIgnoringCase("first");
		BDDAssertions.then(saved.getFirstName()).isNotBlank();

		BDDAssertions.then(saved.getLastName()).isEqualToIgnoringCase("last");
		BDDAssertions.then(saved.getLastName()).isNotBlank();

		BDDAssertions.then(saved.getEmail()).isEqualToIgnoringCase("email@email.com");
		BDDAssertions.then(saved.getLastName()).isNotBlank();

	}

	@Test
	public void repositorySaveShouldMapCorrectly() {

		Customer customer = new Customer(null, "first", "last", "email@email.com");
		Customer saved = this.repository.save(customer);

		BDDAssertions.then(saved.getId()).isNotNull();

		BDDAssertions.then(saved.getFirstName()).isEqualToIgnoringCase("first");
		BDDAssertions.then(saved.getFirstName()).isNotBlank();

		BDDAssertions.then(saved.getLastName()).isEqualToIgnoringCase("last");
		BDDAssertions.then(saved.getLastName()).isNotBlank();

		BDDAssertions.then(saved.getEmail()).isEqualToIgnoringCase("email@email.com");
		BDDAssertions.then(saved.getLastName()).isNotBlank();
	}
}
