package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WithUserDetails
public class AnnotatedClassMessageServiceTests {

	@Autowired
	MessageService messageService;

	@Test
	@WithAnonymousUser
	public void getMessageWhenNotAuthenticatedThenAuthenticationCredentialsNotFoundException() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public void getMessageWhenAuthorizedThenGranted() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithUserDetails("admin")
	public void getMessageWhenAdminThenGranted() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

}
