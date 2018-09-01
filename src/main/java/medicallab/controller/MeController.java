package medicallab.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import medicallab.form.MePasswordForm;
import medicallab.form.MeSettingsForm;
import medicallab.form.validator.MePasswordFormValidator;
import medicallab.form.validator.MeSettingsFormValidator;
import medicallab.misc.Uri;
import medicallab.model.User;
import medicallab.model.service.UserSecurityService;
import medicallab.model.service.UserService;

@Controller
@RequestMapping("/me")
public class MeController {
	
	private final Locale enLocale = new Locale("en");
	
	private final Uri uri;
	private final MessageSource messageSource;
	private final UserService userService;
	private final UserSecurityService userSecurityService;
	private final MeSettingsForm meSettingsForm;
	private final MeSettingsFormValidator meSettingsFormValidator;
	private final MePasswordForm mePasswordForm;
	private final MePasswordFormValidator mePasswordFormValidator;
	
	@Autowired
	public MeController(Uri uri,
			MessageSource messageResource,
			UserService userService,
			UserSecurityService userSecurityService,
			MeSettingsForm meSettingsForm,
			MeSettingsFormValidator meSettingsFormValidator,
			MePasswordForm mePasswordForm,
			MePasswordFormValidator mePasswordFormValidator) {
		
		this.uri			    				= uri;
		this.messageSource  				= messageResource;
		this.userService    				= userService;
		this.userSecurityService			= userSecurityService;
		this.meSettingsForm 				= meSettingsForm;
		this.meSettingsFormValidator 	= meSettingsFormValidator;
		this.mePasswordForm 				= mePasswordForm;
		this.mePasswordFormValidator 	= mePasswordFormValidator;
	}
	

	@GetMapping
	public String me(Model model,
			Authentication auth) {
		User user = userService.findByUsername(auth.getName());
		
		userService.populateFormFromObject(meSettingsForm, user);
		
		model.addAttribute("meSettingsForm", meSettingsForm);
		model.addAttribute("mePasswordForm", mePasswordForm);
		
		return "me";
	}
	
	@PostMapping(params = "meUpdateSettings")
	public String meUpdateSettings(Model model,
			Authentication authentication,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("meSettingsForm") MeSettingsForm meSettingsForm,
			Errors errors) {
		
		
		User user = userService.findByUsername(authentication.getName());
		
		if (! meSettingsForm.anyNewUpdates(user)) {
			model.addAttribute("mePasswordForm", mePasswordForm);
			
			redirectAttributes.addFlashAttribute("warning", 
					messageSource.getMessage("UpdateSettingsWarning", null, enLocale));
		
		} else {
			boolean invalidateMeSession = false;

			meSettingsFormValidator.validate(meSettingsForm, errors);
			
			if (errors.hasErrors()) {
				model.addAttribute("mePasswordForm", mePasswordForm);
				model.addAttribute("meSettingsForm", meSettingsForm);
				return "me";
			}
			
			if (! user.getUsername().equals(meSettingsForm.getUsername())) {
				invalidateMeSession = true;
			}

			userService.populateFormIntoObject(meSettingsForm, user);
			userService.update(user);
			userSecurityService.updateSecurityContext(user, invalidateMeSession);
				
				
			redirectAttributes.addFlashAttribute("success",  
					messageSource.getMessage("UpdateUserSettingsDone", null, enLocale));
		}
		
		return "redirect:" + uri.get("me");
	}
	
	@PostMapping(params = {"meChangePassword"})
	public String meChangePassword(Model model, 
			Authentication authentication,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("mePasswordForm") MePasswordForm mePasswordForm,
			Errors errors) {
						
		mePasswordFormValidator.validate(mePasswordForm, errors);
		
		if (errors.hasErrors()) {
			User user = userService.findByUsername(authentication.getName());
			userService.populateFormFromObject(meSettingsForm, user);
			
			model.addAttribute("meSettingsForm", meSettingsForm);
			
			return "me";
		}
		
		User user = userService.findByUsername(authentication.getName());
		userService.updatePassword(user, mePasswordForm.getNewPassword());
		
		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("UpdateUserPasswordDone", null, enLocale));
		
		return "redirect:" + uri.get("me");
	}
}
