package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Rob Winch
 * @since 5.0
 */
@SpringBootTest
@WithMockUser(roles = "ADMIN")
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnotatedClassMessageServiceTests {

	@Autowired
	MessageService messageService;

	@Test
	@WithAnonymousUser
	public void anonymous() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public void granted() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

}
