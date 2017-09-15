package medicallab.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.model.PatientService;
import medicallab.model.UserService;

@Component
public class FieldValidator {
	
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	

	/**
	 * Rejection Methods
	 * 
	 */
	protected void rejectValue(Errors errors, String errorCode) {
		rejectValue(errors, null, null, errorCode);
	}
	
	protected void rejectValue(Errors errors, String field, String fieldErrorName, String errorCode) {
		String[] errorArgs = getErrorArgs(errorCode, fieldErrorName);
		
		errors.rejectValue(field, errorCode, errorArgs, null);
	}
	
	protected String[] getErrorArgs(String errorCode, String fieldErrorName) {
		switch(errorCode) {
			case "NamingFieldSizeShouldBeValid":
				return new String[] { fieldErrorName, "3", "40" };
				
			case "PasswordFieldSizeShouldBeValid":
				return new String[] { fieldErrorName, "5", "30" };
				
			case "ConfirmationShouldMatchesThePassword":
				return new String[] { fieldErrorName };
				
			default:
				return null;
		}
	}
	
	
	/**
	 * 
	 * Validation Methods
	 */
	
	protected boolean  passwordFieldSizeIsNotValid(String passwordField) {
		if (passwordField == null || passwordField.length() == 0) return true;
		
		passwordField.trim();
		
		return passwordField.length() < 5 || passwordField.length() > 30 ? true : false;
	}
	
	protected boolean currentPasswordIsNotCorrect(UserService userService, String password) {
		if (password == null || "".equals(password)) {
			return true;
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String hashedPassword = userService.findByUsername(username).getHashedPassword();
		
		return bcryptPasswordEncoder.
				matches(password, hashedPassword) ? false : true;
	}
	
	protected boolean confirmationDoesNotEqualNewPassword(String password, String confirmation) {
		return ! password.equals(confirmation) ? true : false;
	}
	
	
	protected boolean namingFieldSizeIsNotValid(String field) {
		if (field == null || field.length() == 0) return true;
		
		field.trim();
	
		return field.length() < 3 || field.length() > 30 ? true : false;
	}
	
	protected boolean phoneFieldSizeIsNotValid(String phoneField) {
		if (phoneField == null || phoneField.length() == 0) return true;
		
		phoneField.trim();
	
		return phoneField.length() > 11 ? true : false;
	}
	
	protected boolean patientIdFieldSizeIsNotValid(String patientIdField) {
		if (patientIdField == null || patientIdField.length() == 0) return true;
		
		patientIdField.trim();
		
		try {
			Long.parseLong(patientIdField);
			
		} catch(NumberFormatException e) {
			System.out.println("NumberFormatException");
			return true;
		}
		
		return patientIdField.length() != 14 ? true : false;
	}
	
	protected boolean ageFieldSizeIsNotValid(Integer ageField) {
		if (ageField == null || ageField == 0) return true;
		
//		ageField.trim();
		
//		try {
//			Integer age = Integer.parseInt(ageField);
//			
//			return age < 0 || age > 200 ? true : false;
//			
//		} catch(NumberFormatException e) {
//			return true;
//		}
		
		return ageField < 0 || ageField > 200 ? true : false;
	}
	
	protected boolean patientIdIsAlreadyExisted(String patientId, PatientService patientService) {
		return patientService.findByPatientId(patientId) == null ? false : true;
	}
	
	
	protected boolean usernameIsAlreadyExisted(UserService userService, String username) {
		return userService.findByUsername(username) != null ? true : false;
	}
	
	protected boolean meUsernameHasChanged(String username) {
		String meUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return meUsername.equals(username) ? false : true;
	}
	
	
	protected boolean genderIsNotValid(String gender) {
		return "male".equals(gender.toLowerCase()) || "female".equals(gender.toLowerCase()) ? false : true;
	}
	
	
	protected boolean roleIsNotSelected(String selectedRole) {
		return selectedRole == null || selectedRole.length() == 0 || "".equals(selectedRole.trim()) ? true : false;
	}
	
	protected boolean selectedRoleIsNotValid(UserService userService, String selectedRole) {
		return ! userService.findRoleList().contains(selectedRole) ? true : false;
	}

}
