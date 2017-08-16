package medicallab.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import medicallab.web.model.User;

public interface UserService extends UserDetailsService {
	User findByUsername(String username);
}
