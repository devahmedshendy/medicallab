package medicallab.model;

import java.util.List;
import java.util.Map;

public interface UserService extends GenericService<User> {
	User findByUsername(String username);
	
	void updatePassword(User user, String newPassword);
	
	void disableUser(User user);
	
	void enableUser(User user);
	
	List<String> findRoleList();
	
	List<String> findGenderList();

	Map<String, String> findSearchFieldList();
}
