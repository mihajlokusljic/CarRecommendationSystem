package kusljic.mihajlo.sbnz.spring.backend.facts;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("lockedOn")
@Expires("1m")
public class AccountLock {
	
	private String username;
	private Date lockedOn;
	
	public AccountLock() {
		super();
	}
	
	public AccountLock(String username) {
		super();
		this.username = username;
	}

	public AccountLock(String username, Date lockedOn) {
		super();
		this.username = username;
		this.lockedOn = lockedOn;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLockedOn() {
		return lockedOn;
	}
	public void setLockedOn(Date lockedOn) {
		this.lockedOn = lockedOn;
	}

}
