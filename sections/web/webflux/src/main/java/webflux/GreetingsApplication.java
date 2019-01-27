package webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class GreetingsApplication {

	@Bean
	RouterFunction<ServerResponse> routes() {
		String nameParamName = "name";
		return route(GET("/greetings"), r -> ok().body(greetingPublisher(""), Greeting.class))
			.andRoute(GET("/greetings/{" + nameParamName + "}"), request -> ok().body(greetingPublisher(request.pathVariable(nameParamName)), Greeting.class));
	}

	private Publisher<Greeting> greetingPublisher(String name) {
		return Mono.just(new Greeting(this.doGreeting(name)));
	}

	private String doGreeting(String name) {
		String template = "Hello%s!";
		if (StringUtils.hasText(name)) {
			return String.format(template, String.format(" %s", name));
		}
		else {
			return String.format(template, "");
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Greeting {
		private String message;
	}

	public static void main(String[] args) {
		SpringApplication.run(GreetingsApplication.class, args);
	}
}
