package medicallab.model.projection;

import org.springframework.data.rest.core.config.Projection;

import medicallab.model.Patient;

@Projection(name = "patientForRequest", types = Patient.class)
public interface PatientForRequest {
	String getFullname();
	String getPatientId();
	String getPhone();
	String getGender();
	String getAge();
}
