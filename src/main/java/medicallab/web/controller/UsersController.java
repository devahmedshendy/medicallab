package medicallab.web.controller;

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

import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.form.AddUserForm;
import medicallab.web.form.EditUserPasswordForm;
import medicallab.web.form.EditUserSettingsForm;
import medicallab.web.form.SearchUserForm;
import medicallab.web.misc.PagedListHolder;
import medicallab.web.model.User;
import medicallab.web.model.service.UserService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired private UserService userService;
	@Autowired private AddUserForm addUserForm;
	@Autowired private EditUserSettingsForm editUserSettingsForm;
	@Autowired private EditUserPasswordForm editUserPasswordForm;
	@Autowired private PagedListHolder<User> pagedListHolder;
	@Autowired private MessageSource messageSource;
	
	private final Locale enLocale = new Locale("en");
	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@RequestMapping(value = { "", "search" })
	public String users(Model model,
			@ModelAttribute("searchUserForm") SearchUserForm searchUserForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "sortField", required=false) String sortField,
			@RequestParam(value = "sortOrder", required=false) String sortOrder,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		Integer currentPage = page;
		Integer nextPage    = currentPage + 1;
		Integer prevPage    = currentPage - 1;
		Integer firstResult = (page - 1) * 10;
		Integer maxResult = 10;
		
		Map<String, String> search = new HashMap<>();
		Map<String, String> sort = new HashMap<>();
		Map<String, String> requestParams = new LinkedHashMap<>();
		Map<String, String> uriListHolder = new HashMap<String, String>();
		
		if (sortField == null && sortOrder == null) {
			sort.put("sortField", "updatedAt");
			sort.put("sortOrder", "DESC");
			
		} else {
			sort.put("sortField", sortField);
			sort.put("sortOrder", "ASC".equals(sortOrder) ? "DESC" : "ASC");
			
			requestParams.put("sortField", sortField);
			requestParams.put("sortOrder", sortOrder);
		}
		
		
		if (searchField != null && searchText != null) {
			search.put("searchField", searchField);
			search.put("searchText", searchText);
			
			requestParams.put("searchField", searchField);
			requestParams.put("searchText", searchText);
		}
		
		pagedListHolder = userService.findPagedList(firstResult, maxResult, search, sort);
		uriListHolder.put("sortFields", "fullname username gender createdAt updatedAt");
		
		model.addAttribute("users", pagedListHolder.getPagedList());
		model.addAttribute("pageSize", pagedListHolder.getPageSize());
		model.addAttribute("totalUsers", pagedListHolder.getNoOfElements());
		model.addAttribute("currentPage", page);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("isLastPage", pagedListHolder.isLastPage());
		model.addAttribute("isFirstPage", pagedListHolder.isFirstPage());

		model.addAttribute("sortField", sort.get("sortField"));
		model.addAttribute("sortOrder", sort.get("sortOrder"));

		model.addAttribute("searchUserForm", searchUserForm);
		model.addAttribute("requestParams", requestParams);
		model.addAttribute("uriListHolder", uriListHolder);
		
		return "users";
	}
	
	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@RequestMapping(value= { "new", "edit/{username}" }, params = { "cancel" })
	public String cancel() {
		return "redirect:/users";
	}
	
	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@GetMapping(path = "edit/{username}")
	public String editUser(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username) {
		
		User user = userService.findByUsername(username);
		
		if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
			return "redirect:/users";
		}
		
		editUserSettingsForm.populateFromObject(user);
		
		model.addAttribute("username", username);
		model.addAttribute("editUserPasswordForm", editUserPasswordForm);
		model.addAttribute("editUserSettingsForm", editUserSettingsForm);
		model.addAttribute("availableRoles", userService.findAvailableRoles());
		model.addAttribute("availableGender", userService.findAvailableGender());

		return "editUser";
	}
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params = { "changeUserSettings" })
	public String changeUserSettings(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@ModelAttribute("editUserSettingsForm") EditUserSettingsForm editUserSettingsForm,
			Errors errors) {
		
		try {
			userService.updateUserByForm(username, editUserSettingsForm, errors);
			
			if (errors.hasErrors()) {
				model.addAttribute("editUserPasswordForm", editUserPasswordForm);
				model.addAttribute("availableRoles", userService.findAvailableRoles());
				model.addAttribute("availableGender", userService.findAvailableGender());
				
				return "editUser";
			}
			
			redirectAttributes.addFlashAttribute("success", 
					messageSource.getMessage("changeSettings.success", null, enLocale));
			
		} catch(NoUpdatedFieldsException e) {
			redirectAttributes.addFlashAttribute("warning", 
					messageSource.getMessage("changeSettings.warning", null, enLocale));
			
		}
		
		return "redirect:/users/edit/" + username;
	}
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "edit/{username}", params = { "changeUserPassword" })
	public String changeUserPassword(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@ModelAttribute("editUserPasswordForm") EditUserPasswordForm editUserPasswordForm,
			Errors errors) {
		
		try {
			userService.updateUserByForm(username, editUserPasswordForm, errors);

		} catch (NoUpdatedFieldsException e) {
			// Currently, there is no need to handle this case 
		}
		
		
		
		if (errors.hasErrors()) {
			model.addAttribute("editUserSettingsForm", editUserSettingsForm);
			model.addAttribute("availableRoles", userService.findAvailableRoles());
			model.addAttribute("availableGender", userService.findAvailableGender());
			
			return "editUser";
		}
		
		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("changePassword.success", null, enLocale));
		
		return "redirect:/users/edit/" + username;
	}

	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@GetMapping(path = "new")
	public String newUser(Model model) {
		model.addAttribute("addUserForm", addUserForm);
		model.addAttribute("availableRoles", userService.findAvailableRoles());
		model.addAttribute("availableGender", userService.findAvailableGender());

		return "newUser";
	}
	
	@PostMapping(path = "new", params = { "addUser" })
	public String addUser(Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("addUserForm") AddUserForm addUserForm, 
			Errors errors) {

		userService.createNewUserByForm(addUserForm, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("availableRoles", userService.findAvailableRoles());
			model.addAttribute("availableGender", userService.findAvailableGender());

			return "newUser";
		}

		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("addUser.success", null, enLocale));

		return "redirect:/users";
	}
	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "{username}", params= { "enable" })
	public String changeUserStatus(RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@RequestParam("enable") boolean enable) {
		
		
		User user = userService.findByUsername(username);
		
		if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
			return "redirect:/users";
		}
		
		String alertType;
		String alertMessage;
		
		if (enable == true) {
			userService.changeStatusByUsername(username, true);
			
			alertType = "success";
			alertMessage = "enableUser.success";
			
		} else if (enable == false) {
			userService.changeStatusByUsername(username, false);
			
			alertType = "success";
			alertMessage = "disableUser.success";
			
		} else {
			alertType = "error";
			alertMessage = "changeUserStatus.failed";
		}
		
		redirectAttributes.addFlashAttribute(alertType,
				messageSource.getMessage(alertMessage, new String[] { username }, enLocale));
		return "redirect:/users";
	}
	
	
	
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT" })
	@PostMapping(path = "{username}", params= { "delete" })
	public String deleteUser(RedirectAttributes redirectAttributes,
			@PathVariable("username") String username,
			@RequestParam("delete") boolean delete) {
		
		User user = userService.findByUsername(username);
		
		if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("permissionDenied", null, enLocale));
			
			return "redirect:/users";
		}
		
		if (delete) {
			userService.deleteByUsername(username);
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("deleteUser.success", new String[] { username }, enLocale));
		}
		
		return "redirect:/users";
	}
}
