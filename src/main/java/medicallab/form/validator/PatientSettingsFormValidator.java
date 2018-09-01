package medicallab.form.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.form.PatientSettingsForm;
import medicallab.model.service.PatientService;


@Component
public class PatientSettingsFormValidator extends FieldValidator implements Validator {
	
	private final PatientService patientService;
	
	public PatientSettingsFormValidator(PatientService patientService) {
		this.patientService = patientService;
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PatientSettingsForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		PatientSettingsForm patientSettingsForm = (PatientSettingsForm) obj;
		Map<String, Object> updatedFields = (HashMap<String, Object>) patientSettingsForm.getUpdatedFields();
		
		for (String field : updatedFields.keySet()) {
			switch (field) {
				case "patientId":
					String patientId = (String) updatedFields.get("patientId");
					
					if (patientIdFieldSizeIsNotValid(patientId)) {
						rejectValue(errors, "patientId", "Patient Id", "PatientIdShouldBeValid");
						
					} else if (patientIdIsAlreadyExisted(patientId, patientService)) {
						rejectValue(errors, "patientId", "PatientId", "PatientIdIsAlreadyAdded");
					}
					break;
				
				
				case "fullname":
					String fullname = (String) updatedFields.get("fullname");
					
					if (namingFieldSizeIsNotValid(fullname)) {
						rejectValue(errors, "fullname", "Fullname", "NamingFieldSizeShouldBeValid");
					}
					break;
					
					
				case "phone":
					String username = (String) updatedFields.get("phone");
					
					if (phoneFieldSizeIsNotValid(username)) {
						rejectValue(errors, "phone", "Phone", "PhoneNumberShouldBeValid");
					}
					
					break;
					
				case "age":
					Integer age = (Integer) updatedFields.get("age");
					
					if (ageFieldSizeIsNotValid(age)) {
						rejectValue(errors, "age", "Age", "AgeShouldBeValid");
					}
					
					break;
					
					
				case "gender": 
					String gender = (String) updatedFields.get("gender");
					
					if (genderIsNotValid(gender)) {
						rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
					}
					break;
			}
		}
	}

	
}