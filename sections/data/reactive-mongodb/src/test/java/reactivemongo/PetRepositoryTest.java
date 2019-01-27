package reactivemongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PetRepositoryTest {

	@Autowired
	private PetRepository petRepository;

	@Test
	public void findByName() throws Exception {


		StepVerifier
			.create(this.petRepository.deleteAll())
			.verifyComplete();

		Flux<Pet> customers =
			Flux
				.just("A", "B", "C", "C", "D")
				.map(name -> new Pet(null, name))
				.flatMap(this.petRepository::save);

		StepVerifier
			.create(customers)
			.expectNextCount(5)
			.verifyComplete();

		StepVerifier
			.create(this.petRepository.findByName("C"))
			.expectNextCount(2)
			.verifyComplete();


	}

}
