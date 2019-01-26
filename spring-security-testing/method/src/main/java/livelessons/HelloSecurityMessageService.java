package livelessons;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@PreAuthorize("hasRole('ADMIN')")
public class HelloSecurityMessageService implements MessageService {

	@Override
	public String getMessage() {
		return "Hello Security!";
	}

}
