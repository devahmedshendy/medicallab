package medicallab.web.model.service;

import java.util.List;

import org.springframework.validation.Errors;

import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.model.User;

public interface UserService extends GenericService<User> {
	User findByUsername(String username);

	void deleteByUsername(String username);

	void createNewUserByForm(Object form, Errors errors);
	
	void updateUserByForm(String username, Object form, Errors errors) throws NoUpdatedFieldsException;
	
	void changeStatusByUsername(String username, boolean enabled);

	List<String> findAvailableRoles();
	
	List<String> findAvailableGender();
	


}
