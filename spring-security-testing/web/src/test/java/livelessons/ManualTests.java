package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManualTests {

	@Autowired
	FilterChainProxy springSecurityFilterChain;

	MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");

	MockHttpServletResponse response = new MockHttpServletResponse();

	MockFilterChain chain = new MockFilterChain();

	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			"user", "password", AuthorityUtils.createAuthorityList("ROLE_USER"));

	@Test
	public void indexWhenSecurityContextHolderThenUnauthorized() throws Exception {

		SecurityContextHolder.getContext().setAuthentication(this.authentication);

		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);

		assertThat(this.response.getStatus())
				.isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Test
	public void indexWhenSetSessionThenUnauthorized() throws Exception {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(this.authentication);
		this.request.getSession().setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				context);

		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);

		assertThat(this.response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}

}
