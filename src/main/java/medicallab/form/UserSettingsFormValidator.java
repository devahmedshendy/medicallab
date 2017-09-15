package medicallab.form;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.model.UserService;

@Component
public class UserSettingsFormValidator extends FieldValidator implements Validator {
	
	private UserService userService;
	
	@Autowired
	public UserSettingsFormValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserSettingsForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserSettingsForm userSettingsForm = (UserSettingsForm) obj;
		Map<String, String> updatedFields = userSettingsForm.getUpdatedFields();
		
//		String firstname = userSettingsForm.getFirstname();
//		if (basicFormValidator.namingFieldSizeIsNotValid(firstname)) {
//			basicFormValidator.rejectValue(errors, "firstname", "Firstname", "NamingFieldSizeShouldBeValid");
//		}
//		
//		
//		String lastname = userSettingsForm.getLastname();
//		if (basicFormValidator.namingFieldSizeIsNotValid(lastname)) {
//			basicFormValidator.rejectValue(errors, "lastname", "Lastname", "NamingFieldSizeShouldBeValid");
//		}
//		
//		
//		String username = userSettingsForm.getUsername();
//		if (basicFormValidator.namingFieldSizeIsNotValid(username)) {
//			basicFormValidator.rejectValue(errors, "username", "Username", "NamingFieldSizeShouldBeValid");
//		
//		} else if (basicFormValidator.usernameIsAlreadyExisted(userService, userSettingsForm.getUsername())) {
//			basicFormValidator.rejectValue(errors, "username", null, "UsernameShouldBeUnique");
//		}
//		
//		
//		String role = userSettingsForm.getRoleName();
//		if (basicFormValidator.roleIsNotSelected(role)) {
//			basicFormValidator.rejectValue(errors, "roleName", null, "RoleShouldBeSelected");
//		
//		} else if (basicFormValidator.selectedRoleIsNotValid(userService, role)) {
//			basicFormValidator.rejectValue(errors, "roleName", null, "RoleShouldBeValid");
//		}
//		
//
//		String gender = userSettingsForm.getGender();
//		if (basicFormValidator.genderIsNotValid(gender)) {
//			basicFormValidator.rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
//		}
		
		
		for (String field : updatedFields.keySet()) {
			switch (field) {
				case "firstname":
					String firstname = updatedFields.get("firstname");
					
					if (namingFieldSizeIsNotValid(firstname)) {
						rejectValue(errors, "firstname", "Firstname", "NamingFieldSizeShouldBeValid");
					}
					break;
					
					
				case "lastname":
					String lastname = updatedFields.get("lastname");
					
					if (namingFieldSizeIsNotValid(lastname)) {
						rejectValue(errors, "lastname", "Lastname", "NamingFieldSizeShouldBeValid");
					}
					break;
					
					
				case "username":
					String username = updatedFields.get("username");
					
					if (namingFieldSizeIsNotValid(username))
						rejectValue(errors, "username", "Username", "NamingFieldSizeShouldBeValid");
					
					else if (usernameIsAlreadyExisted(userService, username))
						rejectValue(errors, "username", null, "UsernameShouldBeUnique");
					break;
					
					
				case "password": 
					String password = updatedFields.get("password");
					String passwordConfirmation = updatedFields.get("passwordConfirmation");
					
					if (passwordFieldSizeIsNotValid(password)) {
						rejectValue(errors, "password", "Password", "PasswordFieldSizeShouldBeValid");

					} else if (! passwordConfirmation.equals(password)) {
						rejectValue(errors, "passwordConfirmation", "Password", "ConfirmationShouldMatchesThePassword");
					}
					break;
					
					
				case "roleName": 
					String role = updatedFields.get("roleName");
					
					if (roleIsNotSelected(role)) {
						rejectValue(errors, "roleName", null, "RoleShouldBeSelected");
					
					} else if (selectedRoleIsNotValid(userService, role)) {
						rejectValue(errors, "roleName", null, "RoleShouldBeValid");
					}
					break;
					
					
				case "gender": 
					String gender = updatedFields.get("gender");
					
					if (genderIsNotValid(gender)) {
						rejectValue(errors, "gender", null, "GenderShouleBeMaleOrFemale");
					}
					break;
			}
		}
	}
}
