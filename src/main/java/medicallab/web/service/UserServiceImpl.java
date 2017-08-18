package medicallab.web.service;

import java.util.Date;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import medicallab.web.dao.UserDAO;
import medicallab.web.form.MePasswordForm;
import medicallab.web.form.MeSettingsForm;
import medicallab.web.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired private UserDAO userDAO;
	@Autowired private User user;
	@Autowired private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Override
	public User findByUsername(String username) {
		try {
			return userDAO.findByUsername(username);
			
		} catch(NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void update(User user) {
		userDAO.update(user);
	}

	@Override
	public void updateSettings(MeSettingsForm meSettingsForm) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		user = findByUsername(currentUsername);
		
		if (! meSettingsForm.settingsIsChanged(user)) return;
		
		meSettingsForm.populateIntoObject(user);
		
		user.setUpdatedAt(new Date());
		
		update(user);
		
		if (currentUsername.equals(user.getUsername()))
			updateSecurityContext(false, user);
		else
			updateSecurityContext(true, user);
	}
	
	
	@Override
	public void updatePassword(MePasswordForm mePasswordForm) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		user = findByUsername(currentUsername);
		user.setHashedPassword( bcryptPasswordEncoder.encode(mePasswordForm.getNewPassword()) );
		user.setUpdatedAt(new Date());
		
		update(user);
		updateSecurityContext(true, user);
	}
	
	
	private void updateSecurityContext(boolean invalidateSession, User user) {
		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		Authentication updatedAuthentication;
		
		user = findByUsername(currentAuthentication.getName());
		
		if (invalidateSession)
			updatedAuthentication = new UsernamePasswordAuthenticationToken(user, null);
			
		else
			updatedAuthentication = new UsernamePasswordAuthenticationToken(user, currentAuthentication.getCredentials(), currentAuthentication.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user = findByUsername(username);
		
		if (user == null)
			throw new UsernameNotFoundException("No such user: " + username);
		
		return new User(user);
	}

}

