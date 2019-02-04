package com.example.basics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicsApplication.class, args);
	}

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {
	private String first, last;
}

@Component
class Foo {

	public String getName() {
		return getClass().getName();
	}
}

@Component
class Bar {

	private final Foo foo;

	Bar(Foo foo) {
		this.foo = foo;
	}

	@Override
	public String toString() {
		return "Bar{" +
			"foo=" + foo +
			'}';
	}
}