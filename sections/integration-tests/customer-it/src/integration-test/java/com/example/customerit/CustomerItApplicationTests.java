package com.example.customerit;

import com.example.customerclient.Customer;
import com.example.customerclient.CustomerClient;
import com.example.customerclient.CustomerClientConfiguration;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.BDDAssertions;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.applications.DeleteApplicationRequest;
import org.cloudfoundry.operations.applications.PushApplicationRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
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

	@SneakyThrows
	private File locationOfJar() {

		ClassPathResource properties = new ClassPathResource("/application.properties");
		Assert.assertTrue("the manifest.yml doesn't exist.", properties.exists());

		File path = properties.getFile();
		File parentFile = path;
		while (true) {
			parentFile = parentFile.getParentFile();
			if (parentFile.getName().equalsIgnoreCase("integration-tests")) {
				break;
			}
		}
		Assert.assertNotNull(parentFile);
		File jarTargetDir = new File(parentFile, "customer-service/target/");
		File[] files = jarTargetDir.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".jar"));
		Assert.assertTrue(files.length > 0);
		return files[0];
	}


	@Test
	public void clientCanTalkToService() throws Exception {

		File jar = this.locationOfJar();
		Assert.assertTrue(jar.exists());

		String anotherString = "btot-customer-service";

		Flux<Void> deleteIfExists = this.cloudFoundryOperations
			.applications()
			.list()
			.filter(as -> as.getName().equalsIgnoreCase(anotherString))
			.doOnNext(applicationSummary ->
				log.info("found " + applicationSummary.getName() + " with URL " +
					applicationSummary.getUrls().iterator().next() + " to delete."))
			.flatMap(as -> cloudFoundryOperations
				.applications()
				.delete(
					DeleteApplicationRequest
						.builder()
						.name(anotherString)
						.deleteRoutes(true)
						.build()
				));

		Mono<Void> push = cloudFoundryOperations
			.applications()
			.push(PushApplicationRequest
				.builder()
				.name(anotherString)
				.path(jar.toPath())
				.build());

		StepVerifier
			.create(deleteIfExists.thenMany(push))
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
