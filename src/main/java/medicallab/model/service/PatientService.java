package medicallab.model.service;

import java.util.List;
import java.util.Map;

import medicallab.model.Patient;

public interface PatientService extends GenericService<Patient> {

	Patient findByPatientId(String patientId);
	
	byte[] findProfileImageByPatientId(String patientId);
	
	Map<String, String> getPatientSearchFieldList();
	
	List<String> getGenderList();
}