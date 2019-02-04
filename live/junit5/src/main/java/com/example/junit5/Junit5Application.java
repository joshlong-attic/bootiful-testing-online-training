package com.example.junit5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Junit5Application {

	public static void main(String[] args) {
		SpringApplication.run(Junit5Application.class, args);
	}
}

@Component
class Foo {
}

