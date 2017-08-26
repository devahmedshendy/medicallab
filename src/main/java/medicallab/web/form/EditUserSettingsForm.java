package medicallab.web.form;

import java.util.HashMap;
import org.springframework.stereotype.Component;

import medicallab.web.model.User;

@Component("editUserSettingsForm")
public class EditUserSettingsForm {

	private String firstname;
	private String lastname;
	private String username;
	private String roleName;
	private String gender;
	
	public EditUserSettingsForm() { }
	
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
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void populateIntoObject(User user) {
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setUsername(this.username);
		user.setGender(this.gender);
	}

	public void populateFromObject(User user) {
		firstname 	= user.getFirstname();
		lastname  	= user.getLastname();
		username  	= user.getUsername();
		gender    	= user.getGender();
		roleName 	= user.getRole().getRoleName();
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
		
		if (! user.getRole().getRoleName().equals(roleName) ) {
			updatedFields.put("roleName", roleName);
		}
		
		if (! user.getGender().equals(gender)) {
			updatedFields.put("gender", gender);
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
