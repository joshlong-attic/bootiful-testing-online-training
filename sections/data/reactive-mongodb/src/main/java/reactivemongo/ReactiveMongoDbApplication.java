package reactivemongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootApplication
public class ReactiveMongoDbApplication {

	public static void main(String args[]) {
		SpringApplication.run(ReactiveMongoDbApplication.class, args);
	}
}


interface PetRepository extends ReactiveMongoRepository<Pet, String> {
	Flux<Pet> findByName(String name);
}

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
class Pet {

	@Id
	private String id;
	private String name;
}