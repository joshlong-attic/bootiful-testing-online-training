package com.example.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Junit5ApplicationTests {

	private final Foo foo;

	public Junit5ApplicationTests(@Autowired Foo foo) {
		this.foo = foo;
	}

	@Test
	public void contextLoads() {
		Assertions.assertNotNull(this.foo);
	}

}

