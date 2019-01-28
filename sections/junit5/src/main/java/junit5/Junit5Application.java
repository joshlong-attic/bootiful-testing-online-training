package junit5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RestController
@SpringBootApplication
public class Junit5Application {

	@GetMapping("/greeting/{name}")
	String greet(@PathVariable String name) {
		return "hello " + name + "!";
	}

	public static void main(String args[]) {
		SpringApplication.run(Junit5Application.class, args);
	}

}


