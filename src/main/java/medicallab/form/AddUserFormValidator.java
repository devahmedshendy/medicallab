package medicallab.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.model.UserService;

@Component
public class AddUserFormValidator extends FieldValidator implements Validator {
	
	private UserService userService;

	@Autowired
	public AddUserFormValidator(UserService userService) {
		this.userService = userService;
	}

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
		
		} else if (usernameIsAlreadyExisted(userService, username)) {
			rejectValue(errors, "username", null, "UsernameShouldBeUnique");
		}
		
		
		if (passwordFieldSizeIsNotValid(password)) {
			rejectValue(errors, "password", "Password", "PasswordFieldSizeShouldBeValid");
		
		} else if (! passwordConfirmation.equals(password)) {
			rejectValue(errors, "passwordConfirmation", "Password", "ConfirmationShouldMatchesThePassword");
		}
		
		
		if (roleIsNotSelected(roleName)) {
			rejectValue(errors, "roleName", null, "RoleShouldBeSelected");
		
		} else if (selectedRoleIsNotValid(userService, roleName)) {
			rejectValue(errors, "roleName", null, "RoleShouldBeValid");
		}
		
		
		if (genderIsNotValid(gender)) {
			rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
		}
		
	}
}
