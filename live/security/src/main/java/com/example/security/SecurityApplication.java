package com.example.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	UserDetailsService userDetailsService() {
		UserDetails admin = User.withDefaultPasswordEncoder().username("admin")
			.password("password").roles("USER", "ADMIN").build();
		UserDetails user = User.withDefaultPasswordEncoder().username("user")
			.password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

}

@RestController
class GreetingsRestController {

	private final GreetingsService greetingsService;

	GreetingsRestController(GreetingsService greetingsService) {
		this.greetingsService = greetingsService;
	}

	@GetMapping("/greetings")
	Greetings greetings() {
		return this.greetingsService.greet();
	}

}

@Service
class GreetingsService {

	@PreAuthorize("authenticated")
	public Greetings greet() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new Greetings("hello, " + authentication.getName());
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Greetings {

	private String greetings;

}