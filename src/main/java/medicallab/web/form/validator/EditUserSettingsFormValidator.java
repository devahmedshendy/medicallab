package medicallab.web.form.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.EditUserSettingsForm;

@Component("editUserSettingsFormValidator")
public class EditUserSettingsFormValidator extends BasicValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EditUserSettingsForm.class.equals(clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void validate(Object obj, Errors errors) {
		Map<String, String> updatedFields = (HashMap<String, String>) obj;
		
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
					
					else if (usernameIsAlreadyExisted(username))
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
						System.out.println("RoleShouldBeSelected");
						rejectValue(errors, "roleName", null, "RoleShouldBeSelected");
					
					} else if (selectedRoleIsNotValid(role)) {
						System.out.println("RoleShouldBeValid");
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
