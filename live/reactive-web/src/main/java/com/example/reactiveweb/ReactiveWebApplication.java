package com.example.reactiveweb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static reactor.core.publisher.Mono.*;


@SpringBootApplication
public class ReactiveWebApplication {

	@Bean
	RouterFunction<ServerResponse> greetings() {
		return route(GET("/greetings"), r -> ok().body(just(new Greeting("hello, world")), Greeting.class));
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebApplication.class, args);
	}

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Greeting {

	private String greeting;
}
