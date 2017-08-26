package medicallab.web.form;

import org.springframework.stereotype.Component;

@Component("editUserPasswordForm")
public class EditUserPasswordForm {
	
		private String password;
		
		private String passwordConfirmation;
		
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
