package medicallab.web.form;

import org.springframework.stereotype.Component;

import medicallab.web.model.User;

@Component("addUserForm")
public class AddUserForm {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String passwordConfirmation;
	private String roleName;
	private String gender;
	
	
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setUsername(username.toLowerCase());
		user.setGender(gender);
	}

}
