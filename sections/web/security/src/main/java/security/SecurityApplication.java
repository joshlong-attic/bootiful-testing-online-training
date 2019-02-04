package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootApplication
public class SecurityApplication {

	public static void main(String args[]) {
		SpringApplication.run(SecurityApplication.class, args);
	}
}


@Controller
class BankController {

	private double balance = 100.00;

	@PostMapping("/transfer")
	String transfer(@RequestParam double amount) {
		this.balance -= amount;
		return "redirect:/?success";
	}

	@GetMapping("/")
	String index(Map<String, Object> model) {
		model.put("balance", this.balance);
		return "index";
	}

}