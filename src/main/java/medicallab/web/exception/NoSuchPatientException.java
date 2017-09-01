package medicallab.web.exception;

import javax.persistence.NoResultException;

public class NoSuchPatientException extends NoResultException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchPatientException(String message) {
        super(message);
    }
}
