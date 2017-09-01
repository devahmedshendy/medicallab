package medicallab.web.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.model.Patient;

public interface PatientService extends GenericService<Patient> {

	Patient findByPatientId(String patientId) throws NoSuchPatientException;
	
	byte[] findProfileImageByPatientId(String patientId);

	void createNewPatientByForm(Object form, Errors errors) throws IOException;

	Patient updatePatientByForm(String patientId, Object form, Errors errors) throws NoUpdatedFieldsException, NoSuchPatientException, IOException;

	void deleteByPatientId(String patientId) throws NoSuchPatientException;

	
	Map<String, String> getPatientSearchFieldList();
	
	List<String> getGenderList();

}
