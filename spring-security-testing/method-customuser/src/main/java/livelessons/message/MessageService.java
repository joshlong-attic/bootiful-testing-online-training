package livelessons.message;

import org.springframework.security.access.prepost.PostAuthorize;

public interface MessageService {

	@PostAuthorize("returnObject?.to?.id == principal?.id")
	Message getMessage();

}
