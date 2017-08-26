package medicallab.web.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

import medicallab.web.model.service.UserService;

public class BasicValidator {
	
	@Autowired BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired UserService userService;
	
	
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
				return new String[] { fieldErrorName, "3", "15" };
				
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
	
	protected boolean currentPasswordIsNotCorrect(String password) {
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
	
		return field.length() < 3 && field.length() > 15 ? true : false;
	}
	
	protected boolean usernameIsAlreadyExisted(String username) {
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
	
	protected boolean selectedRoleIsNotValid(String selectedRole) {
		System.out.println("selectedRole: " + selectedRole);
		return ! userService.findAvailableRoles().contains(selectedRole) ? true : false;
	}
	
}
