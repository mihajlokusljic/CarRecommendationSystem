package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
@Expires("6m")
public class LoginAttempt {
	
	private String username;
	private Date timestamp;
	private boolean successful;
	
	public LoginAttempt() {
		super();
	}
	
	public LoginAttempt(String username, Date timestamp) {
		super();
		this.username = username;
		this.timestamp = timestamp;
	}

	public LoginAttempt(String username, Date timestamp, boolean successful) {
		super();
		this.username = username;
		this.timestamp = timestamp;
		this.successful = successful;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
}
