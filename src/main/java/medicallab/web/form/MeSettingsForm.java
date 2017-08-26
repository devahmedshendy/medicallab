package medicallab.web.form;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import medicallab.web.model.User;

@Component("meSettingsForm")
public class MeSettingsForm {

	private String firstname;
	
	private String lastname;

	private String username;
	
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
	
	public void populateFromObject(User user) {
		this.firstname = user.getFirstname();
		this.lastname  = user.getLastname();
		this.username  = user.getUsername();
	}
	
	public void populateIntoObject(User user) {
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setUsername(this.username.toLowerCase());
	}
	
	public HashMap<String, String> getUpdatedFields(User user) {
		HashMap<String, String> updatedFields = new HashMap<>();
		
		if (! user.getFirstname().equals(firstname)) {
			updatedFields.put("firstname", firstname);
		}
		
		if (! user.getLastname().equals(lastname)) {
			updatedFields.put("lastname", lastname);
		}
		
		if (! user.getUsername().equals(username.toLowerCase())) {
			updatedFields.put("username", username.toLowerCase());
		}

		return updatedFields;
	}
	
	public boolean settingsIsChanged(User user) {
		if (! firstname.equals(user.getFirstname()))
			return true;
		
		if (! lastname.equals(user.getLastname()))
			return true;
		
		if (! username.equals(user.getUsername()))
			return true;
		
		return false;
	}
}
