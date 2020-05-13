package kusljic.mihajlo.sbnz.spring.backend.dto;

public class LoginResponseDTO {
	
	private String jsonWebToken;

	public LoginResponseDTO() {
		super();
	}

	public LoginResponseDTO(String jsonWebToken) {
		super();
		this.jsonWebToken = jsonWebToken;
	}

	public String getJsonWebToken() {
		return jsonWebToken;
	}

	public void setJsonWebToken(String jsonWebToken) {
		this.jsonWebToken = jsonWebToken;
	}
	
}
