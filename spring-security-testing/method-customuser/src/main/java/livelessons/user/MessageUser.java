package livelessons.user;

public class MessageUser {

	private Long id;

	private String email;

	private String password;

	public MessageUser() {
	}

	public MessageUser(Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public MessageUser(MessageUser user) {
		this.id = user.id;
		this.email = user.email;
		this.password = user.password;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
