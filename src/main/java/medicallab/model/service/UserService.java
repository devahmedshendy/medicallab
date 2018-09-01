package medicallab.model.service;

import java.util.List;
import java.util.Map;

import medicallab.model.User;

public interface UserService extends GenericService<User> {
	User findByUsername(String username);
	
	User findById(Long id);
	
	List<User> findByDoctorRole();
	
	void updatePassword(User user, String newPassword);
	
	void disableUser(User user);
	
	void enableUser(User user);
	
	List<String> findRoleList();
	
	List<String> findGenderList();

	Map<String, String> findSearchFieldList();
}
