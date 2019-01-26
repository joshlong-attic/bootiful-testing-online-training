package rsb.testing.consumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@Import(ConsumerApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // <1>
@AutoConfigureWireMock(port = 8080) // <2>
public class WiremockCustomerClientTest {

	// <3>
	@Autowired
	private CustomerClient client;

	@Before
	public void setupWireMock() {

		String json = "[{ \"id\":\"1\", \"name\":\"Jane\"},"
				+ "{ \"id\":\"2\", \"name\":\"John\"}]";

		// <4>
		WireMock.stubFor( //
				WireMock.get("/customers") //
						.willReturn(WireMock.aResponse() //
								.withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE) //
								.withBody(json)));
	}

	@Test
	public void getAllCustomers() {
		Flux<Customer> customers = this.client.getAllCustomers();
		StepVerifier.create(customers) //
				.expectNext(new Customer("1", "Jane")) //
				.expectNext(new Customer("2", "John")) //
				.verifyComplete();
	}

}