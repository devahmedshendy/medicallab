package medicallab.flow.request.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestPatient;
import medicallab.model.Patient;
import medicallab.model.service.PatientService;

@Component
public class RequestPatientValidator {

	private final PatientService patientService;
	
	@Autowired
	public RequestPatientValidator(PatientService patientService) {
		this.patientService = patientService;
	}

	public void validatePatientStep(RequestPatient requestPatient, Errors errors) {
		Patient patient = patientService.findByPatientId(requestPatient.getPatientId());
		
		if (patient == null) {
			errors.reject("PatientIsRequired");
			requestPatient = new RequestPatient();
		}
	}
}
