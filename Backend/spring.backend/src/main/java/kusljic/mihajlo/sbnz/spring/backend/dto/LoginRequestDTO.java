package kusljic.mihajlo.sbnz.spring.backend.dto;

import javax.validation.constraints.NotEmpty;

public class LoginRequestDTO {
	
	@NotEmpty(message = "username is required")
	private String username;
	@NotEmpty(message = "password is required")
	private String password;
	
	public LoginRequestDTO() {
		super();
	}

	public LoginRequestDTO(@NotEmpty(message = "username is required") String username,
			@NotEmpty(message = "password is required") String password) {
		super();
		this.username = username;
		this.password = password;
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

}
