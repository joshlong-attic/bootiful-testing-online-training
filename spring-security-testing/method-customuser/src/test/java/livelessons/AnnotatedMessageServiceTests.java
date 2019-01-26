package livelessons;

import livelessons.message.MessageService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
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
	@WithMockUser("josh@example.com") // will fail
	public void wrongType() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(RuntimeException.class);
	}

	@Test
	@WithUserDetails("josh@example.com") // correct type
	public void grated() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

	@Test
	@WithUserDetails("rob@example.com")
	public void denied() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockMessageUser
	public void grantedWithMock() {
		assertThatCode(() -> this.messageService.getMessage()).doesNotThrowAnyException();
	}

	@Test
	@WithMockMessageUser(id = 2)
	public void deniedWithMock() {
		assertThatCode(() -> this.messageService.getMessage())
				.isInstanceOf(AccessDeniedException.class);
	}

}
