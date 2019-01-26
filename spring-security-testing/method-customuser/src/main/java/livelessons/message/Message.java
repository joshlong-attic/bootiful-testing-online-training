package livelessons.message;

import livelessons.user.MessageUser;

public class Message {

	private MessageUser to;

	private String text;

	public Message() {
	}

	public Message(MessageUser to, String text) {
		this.to = to;
		this.text = text;
	}

	public MessageUser getTo() {
		return this.to;
	}

	public void setTo(MessageUser to) {
		this.to = to;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
