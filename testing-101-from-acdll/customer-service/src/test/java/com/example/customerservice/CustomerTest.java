package com.example.customerservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
	* @author <a href="josh@joshlong.com">Josh Long</a>
	*/
public class CustomerTest {

	private Validator validator;

	@Before
	public void before() throws Throwable {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		this.validator = localValidatorFactoryBean.getValidator();
	}

	@Test
	public void newInstanceWithValidValuesShouldReturnARecord() {
		Customer customer = new Customer(1L, "first", "last", "email@email.com");
		BDDAssertions.then(customer.getFirstName()).isEqualToIgnoringCase("first");
		BDDAssertions.then(customer.getLastName()).isEqualToIgnoringCase("last");
		BDDAssertions.then(customer.getEmail()).isEqualToIgnoringCase("email@email.com");
		BDDAssertions.then(customer.getId()).isNotNull();
	}

	@Test
	public void newInstancecWithInvalidConstraintsShouldProduceConstraintViolations() {
		Customer customer = new Customer(null, null, null, null);
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		BDDAssertions.then(constraintViolations.size()).isEqualTo(3);
	}

}
