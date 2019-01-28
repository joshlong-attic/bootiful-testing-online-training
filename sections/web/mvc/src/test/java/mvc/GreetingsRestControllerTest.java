package mvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@WebMvcTest
@RunWith(SpringRunner.class)
public class GreetingsRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void greet() throws Exception {

		this.mockMvc
			.perform(request(HttpMethod.GET, "/greetings"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath(".message").value("Hello!"));

	}

	@Test
	public void greetJane() throws Exception {

		this.mockMvc
			.perform(request(HttpMethod.GET, "/greetings/Jane"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath(".message").value("Hello Jane!"));
	}
}