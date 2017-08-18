package medicallab.web.controller;

import java.util.Locale;

import javax.validation.Valid;

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

import medicallab.web.form.MePasswordForm;
import medicallab.web.form.MeSettingsForm;
import medicallab.web.model.User;
import medicallab.web.service.UserService;
import medicallab.web.validator.MePasswordFormValidator;
import medicallab.web.validator.MeSettingsFormValidator;

@Controller
@RequestMapping("/me")
public class MeController {
	
	@Autowired private UserService userService;
	@Autowired private MePasswordForm mePasswordForm;
	@Autowired private MeSettingsForm meSettingsForm;
	
	@Autowired private MePasswordFormValidator mePasswordFormValidator;
	@Autowired private MeSettingsFormValidator meSettingsFormValidator;
	
	@Autowired private MessageSource messageSource;
	
	private final Locale enLocale = new Locale("en"); 
	
	@GetMapping
	public String me(Model model, Authentication authentication) {
		User user = (User) userService.findByUsername(authentication.getName());
		
		meSettingsForm.populateFromObject(user);
		model.addAttribute("meSettingsForm", meSettingsForm);
		model.addAttribute("mePasswordForm", mePasswordForm);
		
		return "me";
	}
	
	@GetMapping(params = {"me-cancel"})
	public String meCancel() {
		return "redirect:/";
	}
	
	@PostMapping(params = {"me-change-settings"})
	public String meChangeSettings(Authentication authentication, 
			Model model,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("meSettingsForm") @Valid MeSettingsForm meSettingsForm,
			Errors errors) {
		
		meSettingsFormValidator.validate(meSettingsForm, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("mePasswordForm", mePasswordForm);
			
			return "me";
		}
		
		userService.updateSettings(meSettingsForm);
		
		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("changeSettings.success", null, new Locale("en")));
		
		return "redirect:/me";
		
	}
	
	@PostMapping(params = {"me-change-password"})
	public String meChangePassword(Authentication authentication,
			Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("mePasswordForm") MePasswordForm mePasswordForm,
			Errors errors) {
						
		mePasswordFormValidator.validate(mePasswordForm, errors); 
		
		
		if (errors.hasErrors()) {
			User user = (User) userService.findByUsername(authentication.getName());
			meSettingsForm.populateFromObject(user);
			model.addAttribute("meSettingsForm", meSettingsForm);
			
			return "me";
		}
		
		userService.updatePassword(mePasswordForm);
		
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("changePassword.success", null, enLocale));
		
		return "redirect:/me";
	}
}
