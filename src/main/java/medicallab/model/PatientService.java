package medicallab.model;

import java.util.List;
import java.util.Map;

public interface PatientService extends GenericService<Patient> {

	Patient findByPatientId(String patientId);
	
	byte[] findProfileImageByPatientId(String patientId);

	Map<String, String> getPatientSearchFieldList();
	
	List<String> getGenderList();
}