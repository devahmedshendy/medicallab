package medicallab.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.AddPatientForm;

@Component("addPatientFormValidator")
public class AddPatientFormValidator extends BasicValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AddPatientForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		AddPatientForm addPatientForm = (AddPatientForm) obj;
		
		String fullname 		= addPatientForm.getFullname();
		String patientId  	= addPatientForm.getPatientId();
		String phone 		= addPatientForm.getPhone();
		Integer age  		= addPatientForm.getAge();
		String gender    	= addPatientForm.getGender();
		
		if (patientIdFieldSizeIsNotValid(patientId)) {
			rejectValue(errors, "patientId", "PatientId", "PatientIdShouldBeValid");
		
		} else if (patientIdIsAlreadyExisted(patientId)) {
			rejectValue(errors, "patientId", "PatientId", "PatientIdIsAlreadyAdded");
		}
		
		
		if (namingFieldSizeIsNotValid(fullname)) {
			rejectValue(errors, "fullname", "Fullname", "NamingFieldSizeShouldBeValid");
		}
		
		
		if (phoneFieldSizeIsNotValid(phone)) {
			rejectValue(errors, "phone", "Phone", "PhoneNumberShouldBeValid");
		}
		
		
		if (ageFieldSizeIsNotValid(age)) {
			rejectValue(errors, "age", "Age", "AgeShouldBeValid");
		}
		
		
		if (genderIsNotValid(gender)) {
			rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
		}
	}

}
