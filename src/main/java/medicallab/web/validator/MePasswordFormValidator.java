package medicallab.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.MePasswordForm;
import medicallab.web.service.UserService;

@Component("mePasswordFormValidator")
public class MePasswordFormValidator implements Validator {
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MePasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		MePasswordForm mePasswordForm = (MePasswordForm) obj;
		
		String currentPassword 			= mePasswordForm.getCurrentPassword();
		String newPassword 				= mePasswordForm.getNewPassword();
		String newPasswordConfirmation 	= mePasswordForm.getNewPasswordConfirmation();
		
		if (! passwordSizeIsValid(currentPassword))
			errors.rejectValue("currentPassword", "SizeShouldBeValid");
		
		else if (! currentPasswordIsCorrect(currentPassword))
			errors.rejectValue("currentPassword", "ShouldBeCorrect");
			
		
		if (! passwordSizeIsValid(newPassword))
			errors.rejectValue("newPassword", "SizeShouldBeValid");
		
		else if (! newPasswordMatchesConfirmation(newPassword, newPasswordConfirmation))
			errors.rejectValue("newPasswordConfirmation", "ShouldMatchesNewPassword");
		
		else if (! newPasswordIsDifferentThanCurrentPassword(newPassword, currentPassword))
			errors.rejectValue("newPassword", "ShouldBeDifferentThanCurrentPassword");
	}
	
	
	public boolean newPasswordMatchesConfirmation(String newPassword, String newPasswordConfirmation) {
		return newPasswordConfirmation.equals(newPassword) ? true : false;
	}
	
	
	public boolean passwordSizeIsValid(String passwordField) {
		if (passwordField == null || passwordField.length() == 0) return false;
		
		passwordField.trim();
	
		return passwordField.length() >= 5 && passwordField.length() <= 30 ? true : false;
	}
	
	
	public boolean currentPasswordIsCorrect(String password) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String hashedPassword = userService.findByUsername(username).getHashedPassword();
		
		return passwordEncoder.
				matches(password, hashedPassword) ? true : false;
	}
	
	public boolean newPasswordIsDifferentThanCurrentPassword(String newPassword, String currentPassword) {
		return ! newPassword.equals(currentPassword) ? true : false;
	}
}
