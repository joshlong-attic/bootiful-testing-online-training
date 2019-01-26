package livelessons;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ManualMessageServiceTests {

	@Autowired
	MessageService messageService;

	@After
	public void cleanup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void notAuthenticated() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

	@Test
	public void notAuthorized() {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("user",
				"password", "ROLE_USER");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public void authorized() {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("user",
				"password", "ROLE_ADMIN");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

}
