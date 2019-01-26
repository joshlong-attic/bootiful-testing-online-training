package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Rob Winch
 * @since 5.0
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnotatedMessageServiceTests {

	@Autowired
	MessageService messageService;

	@Test
	public void getMessageWhenNotAuthenticatedThenAuthenticationCredentialsNotFoundException() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

	@Test
	@WithUserDetails
	public void getMessageWhenNotAuthorizedThenGranted() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithUserDetails("admin")
	public void getMessageWhenAdminThenGranted() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

}
