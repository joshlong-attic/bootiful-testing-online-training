package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AnnotatedMessageServiceTests {

	@Autowired
	MessageService messageService;

	@Test
	public void notAuthenticated() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

	@Test
	@WithMockUser // default is ROLE_USER
	public void notAuthorized() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void authorized() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

	@Test
	@WithAdmin
	public void meta() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

}
