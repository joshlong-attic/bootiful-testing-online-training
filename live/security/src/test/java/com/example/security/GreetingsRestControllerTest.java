package com.example.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class GreetingsRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserDetailsService users;

	@Test
	public void greet() throws Exception {
		UserDetails user = this.users.loadUserByUsername("user");
		MockHttpServletRequestBuilder request = get("/greetings")
			.with(user(user));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}
}