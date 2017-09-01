package medicallab.web.form.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.EditPatientSettingsForm;

@Component("editPatientSettingsFormValidator")
public class EditPatientSettingsFormValidator extends BasicValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return EditPatientSettingsForm.class.equals(clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void validate(Object obj, Errors errors) {
		Map<String, Object> updatedFields = (HashMap<String, Object>) obj;
		
		for (String field : updatedFields.keySet()) {
			switch (field) {
				case "patientId":
					String patientId = (String) updatedFields.get("patientId");
					
					if (patientIdFieldSizeIsNotValid(patientId)) {
						rejectValue(errors, "patientId", "Patient Id", "PatientIdShouldBeValid");
						
					} else if (patientIdIsAlreadyExisted(patientId)) {
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
