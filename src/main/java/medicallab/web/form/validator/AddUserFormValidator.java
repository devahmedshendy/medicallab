package medicallab.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.AddUserForm;

@Component("addUserFormValidator")
public class AddUserFormValidator extends BasicValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AddUserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		AddUserForm addUserForm = (AddUserForm) obj;
		
		String firstname 			= addUserForm.getFirstname();
		String lastname  			= addUserForm.getLastname();
		String username 				= addUserForm.getUsername();
		String password  			= addUserForm.getPassword();
		String passwordConfirmation 	= addUserForm.getPasswordConfirmation();
		String roleName				= addUserForm.getRoleName();
		String gender    			= addUserForm.getGender();
		
		if (namingFieldSizeIsNotValid(firstname)) {
			rejectValue(errors, "firstname", "Firstname", "NamingFieldSizeShouldBeValid");
		}
		
		
		if (namingFieldSizeIsNotValid(lastname)) {
			rejectValue(errors, "lastname", "Lastname", "NamingFieldSizeShouldBeValid");
		}
		
		
		if (namingFieldSizeIsNotValid(username)) {
			rejectValue(errors, "username", "Username", "NamingFieldSizeShouldBeValid");
		
		} else if (usernameIsAlreadyExisted(username)) {
			rejectValue(errors, "username", null, "UsernameShouldBeUnique");
		}
		
		
		if (passwordFieldSizeIsNotValid(password)) {
			rejectValue(errors, "password", "Password", "PasswordFieldSizeShouldBeValid");
		
		} else if (! passwordConfirmation.equals(password)) {
			rejectValue(errors, "passwordConfirmation", "Password", "ConfirmationShouldMatchesThePassword");
		}
		
		
		if (roleIsNotSelected(roleName)) {
			rejectValue(errors, "roleName", null, "RoleShouldBeSelected");
		
		} else if (selectedRoleIsNotValid(roleName)) {
			rejectValue(errors, "roleName", null, "RoleShouldBeValid");
		}
		
		
		if (genderIsNotValid(gender)) {
			rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
		}
		
	}
}
