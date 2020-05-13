package kusljic.mihajlo.sbnz.spring.backend.dto;

import kusljic.mihajlo.sbnz.spring.backend.facts.AppUser;

public class AppUserDTO {
	
	private Long id;
	private String username;
	private String fullName;
	private String role;
	
	public AppUserDTO() {
		super();
	}

	public AppUserDTO(Long id, String username, String fullName, String role) {
		super();
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.role = role;
	}
	
	public AppUserDTO(AppUser user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.fullName = user.getFullName();
		this.role = user.getRole().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
