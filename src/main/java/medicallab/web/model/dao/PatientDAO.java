package medicallab.web.model.dao;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.model.Patient;

public interface PatientDAO extends GenericDAO<Patient> {
	Patient findByPatientId(String patientId) throws NoSuchPatientException;
	
	byte[] findProfileImageByPatientId(String patientId);
}
