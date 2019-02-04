package com.example.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingsServiceTest {

	@Autowired
	private GreetingsService greetingsService;

	@Test
	@WithMockUser
	public void withAuthorizedUser ()  {
		Greetings greet = this.greetingsService.greet();
		Assert.assertNotNull(greet);
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void getMessageUnauthenticated() {
		Greetings greet = this.greetingsService.greet();
		Assert.fail();
	}
}
