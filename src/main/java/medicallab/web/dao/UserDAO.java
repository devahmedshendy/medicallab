package medicallab.web.dao;

import medicallab.web.model.User;

public interface UserDAO {
	
	public void create(User user);
	
	public User findByUsername(String username);
	
	public User findById(Long userId);
	
	public void update(User user);
	
	public void delete(User user);
	
}
