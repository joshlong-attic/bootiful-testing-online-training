package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MockMvcTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void indexWhenNotAuthenticatedThenRedirectsToLoginPage() throws Exception {
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML);
		this.mockMvc.perform(request).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void indexWhenUserThenOk() throws Exception {
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML)
				.with(user("rob"));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	@Autowired
	private UserDetailsService users;

	@Test
	public void indexWhenUserDetailsServiceThenOk() throws Exception {
		UserDetails user = this.users.loadUserByUsername("user");
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML)
				.with(user(user));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void indexWhenUserDetailsThenOk() throws Exception {
		UserDetails user = new User("user", "password",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML)
				.with(user(user));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void indexWhenAuthenticationThenOk() throws Exception {
		UserDetails user = new User("user", "password",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), user.getAuthorities());
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML)
				.with(authentication(auth));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void indexWhenSecurityContextThenOk() throws Exception {
		UserDetails user = new User("user", "password",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), user.getAuthorities());
		SecurityContext context = new SecurityContextImpl();
		context.setAuthentication(auth);
		MockHttpServletRequestBuilder request = get("/").accept(MediaType.TEXT_HTML)
				.with(securityContext(context));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void indexWhenWithMockUserThenOk() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void transferWhenNoCsrfTokenThenForbidden() throws Exception {
		MockHttpServletRequestBuilder request = post("/transfer").param("amount", "0")
				.accept(MediaType.TEXT_HTML).with(user("rob"));
		this.mockMvc.perform(request).andExpect(status().isForbidden());
	}

	@Test
	public void transferWhenCsrfTokenThenRedirectOnPost() throws Exception {
		MockHttpServletRequestBuilder request = post("/transfer").param("amount", "0")
				.accept(MediaType.TEXT_HTML).with(user("rob")).with(csrf());
		this.mockMvc.perform(request).andExpect(status().is3xxRedirection());
	}

	@Test
	public void loginWhenSuccessThenAuthenticated() throws Exception {
		this.mockMvc.perform(formLogin()).andExpect(authenticated());
	}

	@Test
	public void loginWhenFailThenNotAuthenticated() throws Exception {
		this.mockMvc.perform(formLogin().user("invalid")).andExpect(unauthenticated());
	}

	@Test
	@WithMockUser
	public void validateInputName() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(content().string(containsString("amount")));
	}

	@Test
	@WithMockUser
	@DirtiesContext
	public void validateTransfer() throws Exception {
		MockHttpServletRequestBuilder request = post("/transfer").param("amount", "10")
				.with(csrf());
		this.mockMvc.perform(request).andExpect(status().is3xxRedirection());
	}

}
