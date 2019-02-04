package selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootApplication
public class SeleniumApplication {

	public static void main(String args[]) {
		SpringApplication.run(SeleniumApplication.class, args);
	}
}

@Controller
class PageController {

	@GetMapping("/p1.php")
	String p1() {
		return "p1";
	}

	@GetMapping("/p2.php")
	String p2() {
		return "p2";
	}
}

