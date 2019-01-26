package livelessons.message;

import org.springframework.stereotype.Component;

@Component
public class BeanMessageService implements MessageService {

	private Message message;

	public BeanMessageService(Message message) {
		this.message = message;
	}

	@Override
	public Message getMessage() {
		return this.message;
	}

}
