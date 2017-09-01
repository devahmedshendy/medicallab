package medicallab.web.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import medicallab.web.model.User;
import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.form.AddUserForm;
import medicallab.web.form.EditUserPasswordForm;
import medicallab.web.form.EditUserSettingsForm;
import medicallab.web.form.MePasswordForm;
import medicallab.web.form.MeSettingsForm;
import medicallab.web.form.validator.AddUserFormValidator;
import medicallab.web.form.validator.EditUserPasswordFormValidator;
import medicallab.web.form.validator.EditUserSettingsFormValidator;
import medicallab.web.form.validator.MePasswordFormValidator;
import medicallab.web.form.validator.MeSettingsFormValidator;
import medicallab.web.misc.PagedListHolder;
import medicallab.web.model.Role;
import medicallab.web.model.dao.UserDAO;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired private UserDAO userDAO;
	@Autowired private RoleService roleService;
	@Autowired private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired private MeSettingsFormValidator meSettingsFormValidator;
	@Autowired private MePasswordFormValidator mePasswordFormValidator;
	@Autowired private AddUserFormValidator addUserFormValidator;
	@Autowired private EditUserSettingsFormValidator editUserSettingsFormValidator;
	@Autowired private EditUserPasswordFormValidator editUserPasswordFormValidator;
	@Autowired private PagedListHolder<User> pagedListHolder;
	@Autowired private UserServiceSecurity userServiceSecurity;

	
	@Override
	public void create(User user) {
		userDAO.create(user);
	}	

	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PagedListHolder<User> findPagedList(Integer firstResult, Integer maxResult, Map<String,String> search, Map<String,String> sort) {
		String nativeQueryString 	 = "SELECT * FROM User WHERE roleName <> 'ROLE_ADMIN' ";
		String nativeCountQueryString = "SELECT COUNT(*) FROM User WHERE roleName <> 'ROLE_ADMIN' ";
		
		String searchField = search.get("searchField");
		String searchText = search.get("searchText");
		
		if ( ! "".equals(searchText) && searchText != null) {
			nativeQueryString += " AND User." + searchField + " LIKE '" + searchText + "%' ";
			nativeCountQueryString += " AND User." + searchField + " LIKE '" + searchText + "%' ";
		}			
		

		String sortField = sort.get("sortField");
		String sortOrder = sort.get("sortOrder");
		
		if ("fullname".equals(sortField)) {
			nativeQueryString += "ORDER BY CONCAT(firstname, ' ', lastname) " + sortOrder;
			
		} else {
			nativeQueryString += "ORDER BY " + sortField + " " + sortOrder;
		}
		
		Map<String, Object> result = userDAO.findPagedList(firstResult, maxResult, nativeQueryString, nativeCountQueryString);
		List<User> userList = (List<User>) result.get("pagedList");
		Integer pageSize    = userList.size();
		Integer noOfElements = (Integer) result.get("noOfElements");
		
		pagedListHolder.setFirstResult(firstResult);
		pagedListHolder.setMaxResult(maxResult);
		pagedListHolder.setPagedList(userList);
		pagedListHolder.setPageSize(pageSize);
		pagedListHolder.setNoOfElements(noOfElements);
		
		return pagedListHolder;
	}
	
	@Override
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}
	
	@Override
	public void update(User user) {
		user.setUpdatedAt(new Date());
		userDAO.update(user);
	}
	
	@Override
	public void delete(User user) {
		userDAO.delete(user);
	}
	
	@Override
	public void deleteByUsername(String username) {
		User user = findByUsername(username);
		
		if (user != null) delete(user);
	}
	
	
	@Override
	public void createNewUserByForm(Object form, Errors errors) {
		User user = new User();
		
		if (form instanceof AddUserForm) {
			AddUserForm addUserForm = (AddUserForm) form;
			
			addUserFormValidator.validate(addUserForm, errors);
			
			if (errors.hasErrors()) return;
		
			addUserForm.populateIntoObject(user);
			
			String hashedPassword = bcryptPasswordEncoder.encode(addUserForm.getPassword()); 
			user.setHashedPassword(hashedPassword);
			
			Role role = roleService.findByRoleName(addUserForm.getRoleName());
			user.setRole(role);
		}

		create(user);
	}
	
	
	@Override
	public void updateUserByForm(String username, Object form, Errors errors) throws NoUpdatedFieldsException {
		boolean invalidateMeSession = false;

		User user = findByUsername(username);
		
		if (form instanceof MeSettingsForm) {
			MeSettingsForm meSettingsForm = (MeSettingsForm) form;
			
			Map<String, String> updatedFields = meSettingsForm.getUpdatedFields(user);
			
			if (updatedFields.size() == 0) throw new NoUpdatedFieldsException();

			meSettingsFormValidator.validate(updatedFields, errors);
			
			if (errors.hasErrors()) return;
			
			if (! user.getUsername().equals(meSettingsForm.getUsername())) {
				invalidateMeSession = true;
			}

			meSettingsForm.populateIntoObject(user);
			
			update(user);
			userServiceSecurity.updateSecurityContext(user, invalidateMeSession);
			
		} else if (form instanceof MePasswordForm) {
			MePasswordForm mePasswordForm = (MePasswordForm) form;
			
			mePasswordFormValidator.validate(mePasswordForm, errors);
			
			if (errors.hasErrors()) return;
			
			String newHashedPassword = bcryptPasswordEncoder.encode(mePasswordForm.getNewPassword());
			
			user.setHashedPassword(newHashedPassword);
			
			update(user);
			userServiceSecurity.updateSecurityContext(user, invalidateMeSession);
			
		} else if (form instanceof EditUserSettingsForm) {
			EditUserSettingsForm editUserSettingsForm = (EditUserSettingsForm) form;
			
			Map<String, String> updatedFields = editUserSettingsForm.getUpdatedFields(user);
			
			if (updatedFields.size() == 0) throw new NoUpdatedFieldsException();

			editUserSettingsFormValidator.validate(updatedFields, errors);
			
			if (errors.hasErrors()) return;
			
			editUserSettingsForm.populateIntoObject(user);
			
			if (updatedFields.get("roleName") != null) {
				Role role = roleService.findByRoleName(editUserSettingsForm.getRoleName());
				user.setRole(role);
			}
			
			update(user);
			userServiceSecurity.expireUserSessionsNow(user, username);
			
		} else if (form instanceof EditUserPasswordForm) {
			EditUserPasswordForm editUserPasswordForm = (EditUserPasswordForm) form;
			
			editUserPasswordFormValidator.validate(editUserPasswordForm, errors);
			
			if (errors.hasErrors()) return;
			
			String hashedPassword = bcryptPasswordEncoder.encode(editUserPasswordForm.getPassword());
			user.setHashedPassword(hashedPassword);
			
			update(user);
			userServiceSecurity.expireUserSessionsNow(user, username);
		}
	}
	
	@Override
	public List<String> findAvailableRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> rolesList = new ArrayList<String>();
				
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			
			if ( "ROLE_ADMIN".equals(authority.getAuthority()) ) {
				rolesList = new ArrayList<>(Arrays.asList("ROLE_DOCTOR", "ROLE_OFFICER"));
				
			} else if ( "ROLE_ROOT".equals(authority.getAuthority()) ) {
				rolesList = new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_OFFICER"));
			}
		}
		
		return rolesList;
	}
	
	@Override
	public List<String> findAvailableGender() {
		return new ArrayList<String>(Arrays.asList("Male", "Female"));
	}
	
	@Override
	public void changeStatusByUsername(String oldUsername, boolean enabled) {
		User user = findByUsername(oldUsername);
		
		user.setEnabled(enabled);

		update(user);
		userServiceSecurity.expireUserSessionsNow(user, user.getUsername());
	}

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		
		if (user == null)
			throw new UsernameNotFoundException("No such user: " + username);
		
		return new User(user);
	}

	@Override
	public User findById(Object id) {
		return userDAO.findById( (Long) id);
	}

}

