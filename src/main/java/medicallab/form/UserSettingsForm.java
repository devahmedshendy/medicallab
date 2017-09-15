package medicallab.form;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import medicallab.model.User;

@Component
@RequestScope
public class UserSettingsForm {

	private String firstname;
	private String lastname;
	private String username;
	private String roleName;
	private String gender;
	
	private Map<String, String> updatedFields = new HashMap<>();
	
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
	
	public Map<String, String> getUpdatedFields() {
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
		
		if (! user.getGender().equals(gender)) {
			updatedFields.put("gender", gender);
		}
		
		if (! user.getRole().getRoleName().equals(roleName)) {
			updatedFields.put("roleName", roleName);
		}
		
		return updatedFields.isEmpty() ? false : true;
	}
}
