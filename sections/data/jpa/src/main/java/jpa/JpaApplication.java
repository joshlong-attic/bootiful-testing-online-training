package jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}

interface PetRepository extends JpaRepository<Pet, Long> {

	Collection<Pet> findByName(String name);
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Pet {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
}



