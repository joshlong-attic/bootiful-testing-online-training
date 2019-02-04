package com.example.rxmongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class RxmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RxmongodbApplication.class, args);
	}

}

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
class Foo {

	@Id
	private String id;

	private String text;

}


interface FooRepository extends ReactiveMongoRepository<Foo, String> {

	Flux<Foo> findByText(String t);
}