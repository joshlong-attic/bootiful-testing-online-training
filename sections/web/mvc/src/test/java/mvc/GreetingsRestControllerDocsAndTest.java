package mvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureRestDocs
public class GreetingsRestControllerDocsAndTest {


	@Autowired
	private MockMvc mockMvc;

	@Test
	public void greet() throws Exception {

		this.mockMvc
			.perform(request(HttpMethod.GET, "/greetings"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath(".message").value("Hello!"))
			.andDo(document("greetings"));

	}

	@Test
	public void greetJane() throws Exception {

		this.mockMvc
			.perform(request(HttpMethod.GET, "/greetings/Jane"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath(".message").value("Hello Jane!"))
			.andDo(document("greetings-by-name"));
		;

	}

}
