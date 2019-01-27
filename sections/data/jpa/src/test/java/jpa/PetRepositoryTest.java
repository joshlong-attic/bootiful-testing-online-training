package jpa;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.stream.Stream;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@DataJpaTest
@RunWith(SpringRunner.class)
public class PetRepositoryTest {

	@Autowired
	private PetRepository petRepository;

	@Test
	public void findByName() throws Exception {

		this.petRepository.deleteAll();

		Assert.assertThat(this.petRepository.count(), Matchers.is(0L));

		Stream
			.of("A", "B", "C", "C", "D")
			.map(name -> new Pet(null, name))
			.forEach(this.petRepository::save);

		Collection<Pet> byName = this.petRepository.findByName("C");

		Assert.assertEquals(byName.size(), 2);
	}
}