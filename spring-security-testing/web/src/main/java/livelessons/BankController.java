package livelessons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Rob Winch
 * @since 5.1
 */
@Controller
public class BankController {

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
