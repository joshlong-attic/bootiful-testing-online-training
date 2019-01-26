package rsb.testing.producer;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import rsb.testing.producer.Customer;
import rsb.testing.producer.CustomerRepository;
import rsb.testing.producer.ProducerApplication;

// <1>
@RunWith(SpringRunner.class)
// <2>
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
public class BaseClass {

	// <3>
	@LocalServerPort
	private int port;

	// <4>
	@MockBean
	private CustomerRepository customerRepository;

	@Before
	public void before() throws Exception {

		// <5>
		Mockito.when(this.customerRepository.findAll()).thenReturn(
				Flux.just(new Customer("1", "Jane"), new Customer("2", "John")));

		// <6>
		RestAssured.baseURI = "http://localhost:" + this.port;
	}

	// <7>
	@Configuration
	@Import(ProducerApplication.class)
	public static class TestConfiguration {

	}

}
