package com.example.basics;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicsApplicationTest {

	@Autowired
	private Foo foo;

	@Autowired
	private Bar bar;

	@Test
	public void oneHasTheOtherInjected() {
		Assert.assertThat(this.bar.toString(), Matchers.containsString(this.foo.getName()));
	}



}