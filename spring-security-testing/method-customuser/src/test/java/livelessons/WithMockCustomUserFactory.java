package livelessons;

import livelessons.user.MessageUser;
import livelessons.user.UserRepository;
import livelessons.user.UserRepositoryUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class WithMockCustomUserFactory
		implements WithSecurityContextFactory<WithMockMessageUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockMessageUser annotation) {
		UserRepository users = mock(UserRepository.class);
		when(users.findByEmail(any())).thenReturn(createUser(annotation));

		UserRepositoryUserDetailsService userDetailsService = new UserRepositoryUserDetailsService(
				users);
		UserDetails principal = userDetailsService.loadUserByUsername(annotation.value());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(new UsernamePasswordAuthenticationToken(principal,
				principal.getPassword(), principal.getAuthorities()));
		return context;
	}

	private MessageUser createUser(WithMockMessageUser annotation) {
		return new MessageUser(annotation.id(), annotation.value(), "notused");
	}

}
