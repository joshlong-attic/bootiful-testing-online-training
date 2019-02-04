package com.example.rxmongodb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@DataMongoTest
public class FooRepositoryTest {

	@Autowired
	private FooRepository fooRepository;

	private Flux<Foo> all() {
		return this.fooRepository.saveAll(Flux.just(new Foo(null, "1"), new Foo(null, "2")));
	}

	@Before
	public void before() throws Exception {
		StepVerifier
			.create(this.fooRepository.deleteAll())
			.verifyComplete();
	}

	@Test
	public void findByText() throws Exception {
		Flux<Foo> byText = this.fooRepository.findByText("1");
		Flux<Foo> saveAndThenFind = all().thenMany(byText);
		StepVerifier
			.create(saveAndThenFind)
			.expectNextCount(1)
			.verifyComplete();
	}

	@Test
	public void saveAll() {
		StepVerifier
			.create(all())
			.expectNextCount(2)
			.verifyComplete();
	}
}