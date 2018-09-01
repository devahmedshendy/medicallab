package medicallab.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import medicallab.form.AddUserForm;
import medicallab.form.MeSettingsForm;
import medicallab.form.UserSettingsForm;
import medicallab.model.Role;
import medicallab.model.User;
import medicallab.model.repository.RoleRepository;
import medicallab.model.repository.UserRepository;
import medicallab.model.specification.UserSpecifications;

@Service
@Transactional
public class UserServiceImpl extends UserSpecifications implements UserService {
	
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo,
			BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	@Override
	public void create(User user) {
		userRepo.save(user);
	}

	@Override
	public void update(User user) {
		user.setUpdatedAt(new Date());
		userRepo.save(user);
	}

	@Override
	public void delete(User user) {
		userRepo.delete(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	@Override
	public User findById(Long id) {
		return userRepo.findOne(id);
	}
	
	@Override
	public List<User> findByDoctorRole() {
		return userRepo.findByDoctorRole();
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		user.setHashedPassword(bcryptPasswordEncoder.encode(newPassword));
		update(user);
	}
	
	@Override
	public void populateFormIntoObject(Object obj, User user) {
		if (obj instanceof UserSettingsForm) {
			UserSettingsForm userSettingsForm = (UserSettingsForm) obj;
			
			user.setFirstname(userSettingsForm.getFirstname());
			user.setLastname(userSettingsForm.getLastname());
			user.setUsername(userSettingsForm.getUsername().toLowerCase());
			user.setGender(userSettingsForm.getGender());
			user.setFullname();
			
			if (! user.getRole().getRoleName().equals(userSettingsForm.getRoleName())) {
				Role role = roleRepo.findByRoleName(userSettingsForm.getRoleName());
				user.setRole(role);
			}
			
		} else if (obj instanceof MeSettingsForm) {
			MeSettingsForm meSettingsForm = (MeSettingsForm) obj;
			
			user.setFirstname(meSettingsForm.getFirstname());
			user.setLastname(meSettingsForm.getLastname());
			user.setUsername(meSettingsForm.getUsername().toLowerCase());
			user.setFullname();
		
		} else if (obj instanceof AddUserForm) {
			AddUserForm addUserForm = (AddUserForm) obj;
			
			user.setFirstname(addUserForm.getFirstname());
			user.setLastname(addUserForm.getLastname());
			user.setUsername(addUserForm.getUsername().toLowerCase());
			user.setGender(addUserForm.getGender());
			
			Role role = roleRepo.findByRoleName(addUserForm.getRoleName());
			user.setRole(role);
			
			user.setHashedPassword(bcryptPasswordEncoder.encode(addUserForm.getPassword()));
		}
	}
	
	@Override
	public void populateFormFromObject(Object obj, User user) {
		if (obj instanceof UserSettingsForm) {
			UserSettingsForm userSettingsForm = (UserSettingsForm) obj;
			
			userSettingsForm.setFirstname(user.getFirstname()); 
			userSettingsForm.setLastname(user.getLastname());
			userSettingsForm.setUsername(user.getUsername());
			userSettingsForm.setGender(user.getGender());
			userSettingsForm.setRoleName(user.getRole().getRoleName());
		
		} 
		else if (obj instanceof MeSettingsForm) {
			MeSettingsForm meSettingsForm = (MeSettingsForm) obj;
			
			meSettingsForm.setFirstname(user.getFirstname());
			meSettingsForm.setLastname(user.getLastname());
			meSettingsForm.setUsername(user.getUsername());
		}
		
	}

	@Override
	public void disableUser(User user) {
		user.setEnabled(false);
		update(user);
	}

	@Override
	public void enableUser(User user) {
		user.setEnabled(true);
		update(user);
	}
	
	@Override
	public Page<User> findPagedList(Map<String,String> queryParams) throws NumberFormatException {
		Page<User> usersPagedList = null;
		
		String searchField = queryParams.get("searchField") == null ? "" : queryParams.get("searchField");
		String searchText = queryParams.get("searchText") == null ? "" : queryParams.get("searchText").trim();
		
		String sortField = queryParams.get("sortField");
		String sortOrder = queryParams.get("sortOrder");
		
		Integer page = Integer.parseInt(queryParams.get("page")) - 1;
		Integer maxResult = Integer.parseInt(queryParams.get("maxResult"));
		
		Sort sort = null;
		if (sortField == null && sortOrder == null) {
			sort = new Sort(Direction.DESC, "updatedAt");

		} else {
			switch(sortField) {
				case "fullname":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "firstname", "lastname");
					break;
					
				case "username":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "username");
					break;
					
				case "gender":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "gender");
					break;
					
				case "createdAt":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "createdAt");
					break;
					
				case "updatedAt":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "updatedAt");
					break;
			}
		}
		
		
		Pageable pageable = new PageRequest(page, maxResult, sort);
		switch(searchField) {
			case "fullname":
				usersPagedList = userRepo.findAll(firstnameConcatLastnameStartingWiths(searchText), pageable);
				break;
				
			case "username":
				usersPagedList = userRepo.findByUsernameStartingWith(searchText, pageable);
				break;
				
			default:
				usersPagedList = userRepo.findAll(pageable);
				break;
		}
		
		return usersPagedList;
	}

	@Override
	public List<String> findRoleList() {
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
	public List<String> findGenderList() {
		return new ArrayList<String>(Arrays.asList("Male", "Female"));
	}
	
	@Override
	public Map<String, String> findSearchFieldList() {
		Map<String, String> searchFieldList = new LinkedHashMap<>();
		searchFieldList.put("fullname", "Full Name");
		searchFieldList.put("username", "Username");
		return searchFieldList;
	}
}
