package medicallab.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import medicallab.model.User;

@Component
public class UserSecurityService {
	
	@Autowired SessionRegistry sessionRegistry;
	
	public void updateSecurityContext(User user, boolean invalidateSession) {
		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (invalidateSession)
			SecurityContextHolder
			.getContext()
			.setAuthentication(
					new UsernamePasswordAuthenticationToken(user, null));
			
		else
			SecurityContextHolder
			.getContext()
			.setAuthentication(new UsernamePasswordAuthenticationToken(user, 
							currentAuthentication.getCredentials(), 
							currentAuthentication.getAuthorities()));
	}
	
	public void expireUserSessionsNow(String username) {
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			if (principal instanceof User) {
				User userDetails = (User) principal;
				
				if (userDetails.getUsername().equals(username)) {
					for (SessionInformation userSession : sessionRegistry.getAllSessions(userDetails, true)) {
						userSession.expireNow();
					}
				}
			}
		}
	}
}
