package medicallab.web.model.dao;

import medicallab.web.model.User;

public interface UserDAO extends GenericDAO<User> {
	User findByUsername(String username);
}
