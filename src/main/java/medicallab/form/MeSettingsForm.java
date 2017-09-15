package medicallab.form;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import medicallab.model.User;

@Component
@SessionScope
public class MeSettingsForm {

	private String firstname;
	private String lastname;
	private String username;
	
	private HashMap<String, String> updatedFields = new HashMap<>();
	
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public HashMap<String, String> getUpdatedFields() {
		return updatedFields;
	}
	
	
	public boolean anyNewUpdates(User user) {
		if (! user.getFirstname().equals(firstname)) {
			updatedFields.put("firstname", firstname);
		}
		
		if (! user.getLastname().equals(lastname)) {
			updatedFields.put("lastname", lastname);
		}
		
		if (! user.getUsername().equals(username)) {
			updatedFields.put("username", username);
		}
		
		return updatedFields.isEmpty() ? false : true;
	}
}
