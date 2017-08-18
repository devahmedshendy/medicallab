package medicallab.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import medicallab.web.form.MeSettingsForm;
import medicallab.web.model.User;
import medicallab.web.service.UserService;

@Component("meSettingsFormValidator")
public class MeSettingsFormValidator implements Validator {
	
	@Autowired UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return MeSettingsForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		MeSettingsForm meSettingsForm = (MeSettingsForm) obj;
		
		String firstname = meSettingsForm.getFirstname();
		String lastname  = meSettingsForm.getLastname();
		String username  = meSettingsForm.getUsername();
		
		if (! fieldSizeIsValid(firstname)) {
			errors.rejectValue("firstname", "SizeShouldBeValid");
		}
		
		if (! fieldSizeIsValid(lastname)) {
			errors.rejectValue("lastname", "SizeShouldBeValid");
		}
		
		if (usernameIsChanged(username)) {
			if (! fieldSizeIsValid(username)) {
				errors.rejectValue("username", "SizeShouldBeValid");
				
			} else if (! usernameIsUnique(username)) {
				errors.rejectValue("username", "ShouldBeUnique");
			}	
		}
	}
	
	
	public boolean fieldSizeIsValid(String field) {
		if (field == null || field.length() == 0) return false;
		
		field.trim();
	
		return field.length() >= 3 && field.length() <= 15 ? true : false;
	}
	
	private boolean usernameIsUnique(String username) {
		User user = userService.findByUsername(username);
		
		return user == null ? true : false;
	}
	
	private boolean usernameIsChanged(String username) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return currentUsername.equals(username) ? false : true;
	}

}
