package kusljic.mihajlo.sbnz.spring.backend.dto;

import javax.validation.constraints.NotEmpty;

public class UserRegistrationDTO {
	
	@NotEmpty(message = "username is required")
	private String username;
	@NotEmpty(message = "password is required")
	private String password;
	private String fullName;
	
	public UserRegistrationDTO() {
		super();
	}

	public UserRegistrationDTO(@NotEmpty(message = "username is required") String username,
			@NotEmpty(message = "password is required") String password, String fullName) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
