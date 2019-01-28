package junit5;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class Junit5ApplicationTest {

	private final MockMvc mockMvc;

	Junit5ApplicationTest(@Autowired MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	public void greet() throws Exception {
		this.mockMvc
			.perform(get("/greeting/world"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
			.andExpect(content().string("hello world!"));
	}
}