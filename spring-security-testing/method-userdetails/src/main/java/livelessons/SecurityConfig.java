package livelessons;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		UserDetails admin = User.withDefaultPasswordEncoder().username("admin")
				.password("password").roles("USER", "ADMIN").build();
		UserDetails user = User.withDefaultPasswordEncoder().username("user")
				.password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

}
