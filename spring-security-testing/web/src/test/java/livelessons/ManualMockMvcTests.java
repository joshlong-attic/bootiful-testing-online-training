package livelessons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Rob Winch
 * @since 5.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ManualMockMvcTests {

	@Autowired
	WebApplicationContext context;

	@Autowired
	Filter springSecurityFilterChain;

	@Test
	public void contextFails() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.addFilters(this.springSecurityFilterChain).build();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				"user", "password", AuthorityUtils.createAuthorityList("ROLE_USER"));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
	}

	@Test
	public void sessionWorks() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.addFilters(this.springSecurityFilterChain).build();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				"user", "password", AuthorityUtils.createAuthorityList("ROLE_USER"));

		SecurityContext context = new SecurityContextImpl();
		context.setAuthentication(authentication);

		MockHttpServletRequestBuilder request = get("/")
				.sessionAttr(SPRING_SECURITY_CONTEXT_KEY, context);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void contextApplySpringSecurity() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(springSecurity()).build();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				"user", "password", AuthorityUtils.createAuthorityList("ROLE_USER"));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(get("/")).andExpect(status().isOk());

		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void validateInput() {

	}

}
