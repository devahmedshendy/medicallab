package medicallab.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.MePasswordForm;

@Component("mePasswordFormValidator")
public class MePasswordFormValidator extends BasicValidator implements Validator {
	
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
		

		if (currentPasswordIsNotCorrect(currentPassword))
			rejectValue(errors, "currentPassword", null, "CurrentPasswordIsNotCorrect");
			
		
		if (passwordFieldSizeIsNotValid(newPassword))
			rejectValue(errors, "newPassword", "New Password", "PasswordFieldSizeShouldBeValid");
		
		else if (newPassword.equals(currentPassword))
			rejectValue(errors, "newPassword", null, "NewPasswordShouldBeDifferentThanCurrentPassword");
		
		else if (! newPasswordConfirmation.equals(newPassword))
			rejectValue(errors, "newPasswordConfirmation", "New Password", "ConfirmationShouldMatchesThePassword");
	}
	
}
