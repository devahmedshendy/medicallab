package medicallab.form;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserPasswordForm {
	
	private String password;
	private String passwordConfirmation;
	

	public UserPasswordForm() {  }
	
	
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
}
