package medicallab.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import medicallab.form.AddUserForm;
import medicallab.form.AddUserFormValidator;
import medicallab.form.UserPasswordForm;
import medicallab.form.UserPasswordFormValidator;
import medicallab.form.UserSettingsForm;
import medicallab.form.UserSettingsFormValidator;
import medicallab.form.SearchUserForm;
import medicallab.misc.Uri;
import medicallab.model.User;
import medicallab.model.UserSecurityService;
import medicallab.model.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	private Locale enLocale = new Locale("en");
	
	private final Uri uri;
	private final MessageSource messageSource;
	private final UserService userService;
	private final AddUserForm addUserForm;
	private final AddUserFormValidator addUserFormValidator;
	private final UserSettingsForm userSettingsForm;
	private final UserSettingsFormValidator userSettingsFormValidator;
	private final UserPasswordFormValidator userPasswordFormValidator;
	private final UserPasswordForm userPasswordForm;
	private final UserSecurityService userSecurityService;
	
	@Autowired
	public UsersController(Uri uri,
			MessageSource messageSource,
			UserService userService,
			AddUserForm addUserForm,
			AddUserFormValidator addUserFormValidator,
			UserSettingsForm userSettingsForm,
			UserSettingsFormValidator userSettingsFormValidator,
			UserPasswordForm userPasswordForm,
			UserPasswordFormValidator userPasswordFormValidator,
			UserSecurityService userSecurityService) {
		
		this.uri					 		= uri;
		this.messageSource        		= messageSource;
		this.userService 				= userService;
		this.addUserForm 				= addUserForm;
		this.addUserFormValidator   		= addUserFormValidator;
		this.userSettingsForm 			= userSettingsForm;
		this.userSettingsFormValidator 	= userSettingsFormValidator;
		this.userPasswordForm 			= userPasswordForm;
		this.userPasswordFormValidator  = userPasswordFormValidator;
		this.userSecurityService 		= userSecurityService;
	}
	
	/*
	 * Sort/Page/Search Users
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@RequestMapping(value = { "", "search" })
	public String users(Model model,
			HttpServletRequest request,
			@ModelAttribute("searchUserForm") SearchUserForm searchUserForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "sortField", required=false) String sortField,
			@RequestParam(value = "sortOrder", required=false) String sortOrder,
			@RequestParam(value = "maxResult", defaultValue = "10") Integer maxResult,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("page", page.toString());
		queryParams.put("maxResult", maxResult.toString());
		queryParams.put("sortField", sortField);
		queryParams.put("sortOrder", sortOrder);
		queryParams.put("searchField", searchField);
		queryParams.put("searchText", searchText);

		
		Page<User> usersPagedList = userService.findPagedList(queryParams);
		
		uri.setRequestUri(request.getRequestURI());
		uri.setQueryParams(queryParams);
		
		model.addAttribute("usersPage", usersPagedList);
		model.addAttribute("pageSize", usersPagedList.getNumberOfElements());
		model.addAttribute("totalUsers", usersPagedList.getTotalElements());
		model.addAttribute("page", page);
		
		model.addAttribute("userSearchFieldList", userService.findSearchFieldList());
		model.addAttribute("genderList", userService.findGenderList());

		model.addAttribute("sortField", sortField == null ? "updatedAt" : sortField);
		model.addAttribute("sortOrder", sortOrder == null ? "DESC" : sortOrder);

		model.addAttribute("searchUserForm", searchUserForm);
		
		return "users";
	}
	
	/*
	 * Edit User
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@RequestMapping(value= { "new", "edit/{username}" }, params = { "cancel" })
	public String cancel() {
		return "redirect:" + uri.get("users");
	}
	
	/*
	 * Edit User Settings
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@GetMapping(path = "edit/{username}")
	public String editUser(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username) {
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("NoSuchUser", new String[] { username }, enLocale));
			
			return "redirect:" + uri.get("users");
		
		} else {
			if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
				redirectAttributes.addFlashAttribute("error", 
						messageSource.getMessage("permissionDenied", null, enLocale));
				
				return "redirect:" + uri.get("users");
			}
			
			userService.populateFormFromObject(userSettingsForm, user);
			
			model.addAttribute("username", username);
			model.addAttribute("userPasswordForm", userPasswordForm);
			model.addAttribute("userSettingsForm", userSettingsForm);
			model.addAttribute("roleList", userService.findRoleList());
			model.addAttribute("availableGender", userService.findGenderList());
			
		}
		
		return "edit-user";
	}
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params = { "updateUserSettings" })
	public String updateUserSettings(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@ModelAttribute("userSettingsForm") UserSettingsForm userSettingsForm,
			Errors errors) {
		
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchUser", new String[] { username }, enLocale));
		
		} else {
		
			if (! userSettingsForm.anyNewUpdates(user)) {
				redirectAttributes.addFlashAttribute("warning", 
						messageSource.getMessage("UpdateSettingsWarning", null, enLocale));
			
			} else {
				userSettingsFormValidator.validate(userSettingsForm, errors);
				
				if (errors.hasErrors()) {
					model.addAttribute("userPasswordForm", userPasswordForm);
					model.addAttribute("roleList", userService.findRoleList());
					model.addAttribute("availableGender", userService.findGenderList());
					
					return "edit-user";
				}
				
				userService.populateFormIntoObject(userSettingsForm, user);
				
				userService.update(user);
				userSecurityService.expireUserSessionsNow(username);
				
				redirectAttributes.addFlashAttribute("success", 
						messageSource.getMessage("UpdateUserSettingsDone", null, enLocale));
			}
		}
		
		return "redirect:" + uri.get("editUser", user.getUsername());
	}
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params = { "updateUserPassword" })
	public String updateUserPassword(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@ModelAttribute("userPasswordForm") UserPasswordForm userPasswordForm,
			Errors errors) {
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchUser", new String[] { username }, enLocale));
		
		} else {
			userPasswordFormValidator.validate(userPasswordForm, errors);
			
			if (errors.hasErrors()) {
				model.addAttribute("userSettingsForm", userSettingsForm);
				model.addAttribute("roleList", userService.findRoleList());
				model.addAttribute("availableGender", userService.findGenderList());
				
				return "edit-user";
			}
			
			userService.updatePassword(user, userPasswordForm.getPassword());
			userSecurityService.expireUserSessionsNow(username);
			
			redirectAttributes.addFlashAttribute("success", 
					messageSource.getMessage("UpdateUserPasswordDone", null, enLocale));
		}
		
		return "redirect:" + uri.get("editUser", username);
	}
	
	/*
	 * Add User	
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@GetMapping(path = "new")
	public String newUser(Model model) {
		model.addAttribute("addUserForm", addUserForm);
		model.addAttribute("roleList", userService.findRoleList());
		model.addAttribute("availableGender", userService.findGenderList());

		return "add-user";
	}
	
	@PostMapping(path = "new", params = { "addUser" })
	public String addUser(Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("addUserForm") AddUserForm addUserForm, 
			Errors errors) {

		addUserFormValidator.validate(addUserForm, errors);

		if (errors.hasErrors()) {
			model.addAttribute("roleList", userService.findRoleList());
			model.addAttribute("availableGender", userService.findGenderList());

			return "add-user";
		}
		
		User user = new User();
		userService.populateFormIntoObject(addUserForm, user);
		userService.create(user);

		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("AddUserDone", null, enLocale));

		return "redirect:" + uri.get("users");
	}
	
	/*
	 * Enable/Disabled User
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params= { "enable" })
	public String enableUser(RedirectAttributes redirectAttributes,
			@PathVariable("username") String username) {
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchUser", new String[] { username }, enLocale));
		
		} else if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
		} else {
			userService.enableUser(user);
			userSecurityService.expireUserSessionsNow(user.getUsername());
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("EnableUserDone", new String[] { username }, enLocale));
		}
		
		return "redirect:" + uri.get("users");
	}
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params= { "disable" })
	public String disableUser(RedirectAttributes redirectAttributes,
			@PathVariable("username") String username) {
		
		String alertType;
		String alertMessage;
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			alertType = "error";
			alertMessage = "NoSuchUser";
			
			redirectAttributes.addFlashAttribute(alertType,
					messageSource.getMessage(alertMessage, new String[] { username }, enLocale));
		}
		
		if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
		} else {
			userService.disableUser(user);
			userSecurityService.expireUserSessionsNow(user.getUsername());
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("DisableUserDone", new String[] { username }, enLocale));
		}
		
		
		return "redirect:" + uri.get("users");
	}
	
	/*
	 * Delete User 
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params= { "delete" })
	public String deleteUser(RedirectAttributes redirectAttributes,
			@PathVariable("username") String username) {
		
		User user = userService.findByUsername(username);
		
		if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
		} else {
			userService.delete(user);
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("DeletePatientDone", new String[] { username }, enLocale));
		}
		
		return "redirect:" + uri.get("users");
	}
}
