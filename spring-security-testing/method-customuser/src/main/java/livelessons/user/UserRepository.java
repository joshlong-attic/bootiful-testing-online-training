package livelessons.user;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {

	private Map<String, MessageUser> emailToUser = new HashMap<>();

	public UserRepository(MessageUser... users) {
		for (MessageUser user : users) {
			this.emailToUser.put(user.getEmail(), user);
		}
	}

	public MessageUser findByEmail(String email) {
		return this.emailToUser.get(email);
	}

}
