package medicallab.web.service;

import medicallab.web.form.MePasswordForm;
import medicallab.web.form.MeSettingsForm;
import medicallab.web.model.User;

public interface UserService {
	
	public User findByUsername(String username);
	
	public void update(User user);
	
	public void updateSettings(MeSettingsForm meSettingsForm);
	
	public void updatePassword(MePasswordForm mePasswordForm);
}
