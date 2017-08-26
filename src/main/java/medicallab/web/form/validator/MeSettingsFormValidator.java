package medicallab.web.form.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.MeSettingsForm;

@Component("meSettingsFormValidator")
public class MeSettingsFormValidator extends BasicValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MeSettingsForm.class.equals(clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void validate(Object obj, Errors errors) {
		Map<String, String> updatedFields = (HashMap<String, String>) obj;
		
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
							
						else if (usernameIsAlreadyExisted(username))
							rejectValue(errors, "username", null, "UsernameShouldBeUnique");
					}
					break;
			}
		}
	}
}
