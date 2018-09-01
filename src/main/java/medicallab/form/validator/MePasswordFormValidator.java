package medicallab.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.form.MePasswordForm;
import medicallab.model.service.UserService;

@Component
public class MePasswordFormValidator extends FieldValidator implements Validator {
	
	private UserService userService;
	
	public MePasswordFormValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MePasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		MePasswordForm MePasswordForm = (MePasswordForm) obj;
		
		String currentPassword 			= MePasswordForm.getCurrentPassword();
		String newPassword 				= MePasswordForm.getNewPassword();
		String newPasswordConfirmation 	= MePasswordForm.getNewPasswordConfirmation();
		

		if (currentPasswordIsNotCorrect(userService, currentPassword)) {
			rejectValue(errors, "currentPassword", null, "CurrentPasswordIsNotCorrect");
		}
			
		
		if (passwordFieldSizeIsNotValid(newPassword)) {
			rejectValue(errors, "newPassword", "New Password", "PasswordFieldSizeShouldBeValid");
		
		} else if (newPassword.equals(currentPassword)) {
			rejectValue(errors, "newPassword", null, "NewPasswordShouldBeDifferentThanCurrentPassword");
		
		} else if (! newPasswordConfirmation.equals(newPassword)) {
			rejectValue(errors, "newPasswordConfirmation", "New Password", "ConfirmationShouldMatchesThePassword");
		}
	}
	
}