package medicallab.form;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.model.UserService;

@Component
public class MeSettingsFormValidator extends FieldValidator implements Validator {
	
	private UserService userService;
	
	@Autowired
	public MeSettingsFormValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MeSettingsForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		MeSettingsForm MeSettingsForm = (MeSettingsForm) obj;
		Map<String, String> updatedFields = MeSettingsForm.getUpdatedFields();
		
		for (String field : updatedFields.keySet()) {
			switch (field) {
				case "firstname":
					String firstname = updatedFields.get("firstname");
					
					if (namingFieldSizeIsNotValid(firstname))
						rejectValue(errors, "firstname", "Firstname", "NamingFieldSizeShouldBeValid");
					break;
					
					
				case "lastname":
					String lastname = updatedFields.get("lastname");
					
					if (namingFieldSizeIsNotValid(lastname))
						rejectValue(errors, "lastname", "Lastname", "NamingFieldSizeShouldBeValid");
					break;
					
					
				case "username":
					String username = updatedFields.get("username");
					
					if (meUsernameHasChanged(username)) {
						if (namingFieldSizeIsNotValid(username))
							rejectValue(errors, "username", "Username", "NamingFieldSizeShouldBeValid");
							
						else if (usernameIsAlreadyExisted(userService, username))
							rejectValue(errors, "username", null, "UsernameShouldBeUnique");
					}
					break;
			}
		}
	}
}
