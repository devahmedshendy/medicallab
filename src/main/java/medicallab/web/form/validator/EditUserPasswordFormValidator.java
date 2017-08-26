package medicallab.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.EditUserPasswordForm;

@Component("editUserPasswordFormValidator")
public class EditUserPasswordFormValidator extends BasicValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return EditUserPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		EditUserPasswordForm editUserPasswordForm = (EditUserPasswordForm) obj;
		
		String password 				= editUserPasswordForm.getPassword();
		String passwordConfirmation 	= editUserPasswordForm.getPasswordConfirmation();
		

		if (passwordFieldSizeIsNotValid(password))
			rejectValue(errors, "password", "Password", "PasswordFieldSizeShouldBeValid");
		
		else if (! passwordConfirmation.equals(password))
			rejectValue(errors, "passwordConfirmation", "Password", "ConfirmationShouldMatchesThePassword");
	}
	
}
