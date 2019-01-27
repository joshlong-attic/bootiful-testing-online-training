package mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class GreetingsApplication {

	public static void main(String args[]) {
		SpringApplication.run(GreetingsApplication.class, args);
	}
}

@RestController
class GreetingsRestController {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Greeting {
		private String message;
	}

	private String doGreeting(String name) {
		String msg = "Hello%s!";
		if (StringUtils.hasText(name)) {
			return String.format(msg, String.format(" %s", name));
		}
		else {
			return String.format(msg, "");
		}
	}

	@GetMapping("/greetings")
	Greeting greet() {
		return new Greeting(this.doGreeting(""));
	}

	@GetMapping("/greetings/{name}")
	Greeting greetByName(@PathVariable String name) {
		return new Greeting(this.doGreeting(name));
	}
}

