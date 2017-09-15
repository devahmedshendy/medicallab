package medicallab.form;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserPasswordFormValidator extends FieldValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserPasswordForm userPasswordForm = (UserPasswordForm) obj;
		
		String password 				= userPasswordForm.getPassword();
		String passwordConfirmation 	= userPasswordForm.getPasswordConfirmation();
		

		if (passwordFieldSizeIsNotValid(password))
			rejectValue(errors, "password", "Password", "PasswordFieldSizeShouldBeValid");
		
		else if (! passwordConfirmation.equals(password))
			rejectValue(errors, "passwordConfirmation", "Password", "ConfirmationShouldMatchesThePassword");
	}
}
