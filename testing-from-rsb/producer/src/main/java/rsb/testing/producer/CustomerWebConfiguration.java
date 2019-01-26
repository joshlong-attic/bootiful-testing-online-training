package rsb.testing.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
class CustomerWebConfiguration {

	@Bean // <1>
	RouterFunction<ServerResponse> routes(CustomerRepository customerRepository) {
		return route(GET("/customers"), // <2>
				request -> ok().body(customerRepository.findAll(), Customer.class));
	}

}
