package rsb.testing.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Import(ConsumerApplication.class)
@AutoConfigureStubRunner(ids = "rsb:producer:+:8080", // <1>
		stubsMode = StubRunnerProperties.StubsMode.LOCAL) // <2>
public class StubRunnerCustomerClientTest {

	@Autowired
	private CustomerClient client;

	@Test
	public void getAllCustomers() {
		Flux<Customer> customers = this.client.getAllCustomers();
		StepVerifier //
				.create(customers) //
				.expectNext(new Customer("1", "Jane")) //
				.expectNext(new Customer("2", "John")) //
				.verifyComplete();
	}

}