package medicallab.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import medicallab.web.dao.UserDAO;
import medicallab.web.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	public User findByUsername(String username) {
		User user = new User();

		try {
			user = userDao.findByUsername(username);
			
		} catch(Exception e) {
			return null;
//			e.printStackTrace();
		}
		
		return user; 
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("No such user: " + username);
		}
		
		return new User(user);
	}
}
