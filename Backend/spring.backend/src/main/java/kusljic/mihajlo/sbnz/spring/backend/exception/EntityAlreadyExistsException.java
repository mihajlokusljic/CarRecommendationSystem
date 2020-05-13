package kusljic.mihajlo.sbnz.spring.backend.exception;

public class EntityAlreadyExistsException extends RuntimeException {
	
	public EntityAlreadyExistsException(String message) {
		super(message);
	}

}
