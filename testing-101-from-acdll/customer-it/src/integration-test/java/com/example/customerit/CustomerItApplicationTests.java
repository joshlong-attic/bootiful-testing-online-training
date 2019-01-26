package com.example.customerit;

import com.example.customerclient.Customer;
import com.example.customerclient.CustomerClient;
import com.example.customerclient.CustomerClientConfiguration;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.BDDAssertions;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.applications.ApplicationManifestUtils;
import org.cloudfoundry.operations.applications.DeleteApplicationRequest;
import org.cloudfoundry.operations.applications.PushApplicationManifestRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CustomerItApplication.class,
	CustomerClientConfiguration.class,
	CloudFoundryClientConfiguration.class})
public class CustomerItApplicationTests {

	@Autowired
	private CloudFoundryOperations cloudFoundryOperations;

	@Autowired
	private CustomerClient client;

	private File manifest = new File("../manifest.yml");

	@Test
	public void clientCanTalkToService() {

		String anotherString = "btot-customer-service";

		Flux<Void> deleteIfExists = this.cloudFoundryOperations
			.applications()
			.list()
			.filter(as -> as.getName().equalsIgnoreCase(anotherString))
			.doOnNext(applicationSummary ->
				log.info("found " + applicationSummary.getName() + " with URL " +
					applicationSummary.getUrls().iterator().next() + " to delete."))
			.flatMap(as ->
				this.cloudFoundryOperations
					.applications()
					.delete(
						DeleteApplicationRequest
							.builder()
							.name(anotherString)
							.deleteRoutes(true)
							.build()
					));

		Mono<Void> pushManifest = cloudFoundryOperations
			.applications()
			.pushManifest(PushApplicationManifestRequest
				.builder()
				.manifest(ApplicationManifestUtils.read(this.manifest.toPath()).get(0))
				.build());

		StepVerifier
			.create(
				deleteIfExists.thenMany(pushManifest)
			)
			.verifyComplete();

		Customer customerById = this.client.getCustomerById(1L);
		BDDAssertions.then(customerById.getId()).isEqualTo(1L);
		BDDAssertions.then(customerById.getFirstName()).isEqualTo("a");
		BDDAssertions.then(customerById.getLastName()).isEqualTo("a");
		BDDAssertions.then(customerById.getEmail()).isEqualTo("a@a.com");

		StepVerifier
			.create(deleteIfExists)
			.verifyComplete();
	}

}
