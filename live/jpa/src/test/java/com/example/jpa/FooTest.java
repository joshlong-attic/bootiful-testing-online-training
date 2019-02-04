package com.example.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@DataJpaTest
@RunWith(SpringRunner.class)
public class FooTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private FooRepository fooRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void foo() throws Exception {

		Foo words = new Foo(null, "Words");
		this.entityManager.persist(words);
		Assert.assertNotNull(words.getId());

		Foo bob = this.testEntityManager
			.persistFlushFind(new Foo(null, "Bob"));
		Assert.assertNotNull(bob.getId());


		Foo one = this.fooRepository.save(new Foo(null, "Foo"));
		Foo two = this.fooRepository.save(new Foo(null, "Bar"));
		Assert.assertEquals(one.getName(), "Foo");
		Assert.assertNotNull(one.getId());
		Assert.assertEquals(two.getName(), "Bar");
		Assert.assertNotNull(two.getId());


	}

}