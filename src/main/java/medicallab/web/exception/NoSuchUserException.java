package medicallab.web.exception;

import javax.persistence.NoResultException;

public class NoSuchUserException extends NoResultException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchUserException(String message) {
		super(message);
	}

}
