package kusljic.mihajlo.sbnz.spring.backend.facts;

public class AppUser {
	
	private String username;
	private String passwordHash;
	private String salt;
	private String fullName;
	private UserRole role;
	
	public AppUser() {
		super();
	}
	
	public AppUser(String username, String passwordHash, String salt, String fullName, UserRole role) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.salt = salt;
		this.fullName = fullName;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
}
