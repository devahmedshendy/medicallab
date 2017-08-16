package medicallab.web.dao;

import medicallab.web.model.User;

public interface UserDAO {
	 User findByUsername(String username);
}
