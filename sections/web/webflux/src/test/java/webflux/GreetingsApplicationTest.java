package webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@WebFluxTest
@RunWith(SpringRunner.class)
public class GreetingsApplicationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void greetings() throws Exception {

		this.webTestClient
			.get()
			.uri("/greetings")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			.expectBody().jsonPath(".message").isEqualTo("Hello!");
	}

	@Test
	public void greetingsJane() throws Exception {

		this.webTestClient
			.get()
			.uri("/greetings/{name}", "Jane")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			.expectBody().jsonPath(".message").isEqualTo("Hello Jane!");
	}

}