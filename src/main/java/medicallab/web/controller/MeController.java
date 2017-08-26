package medicallab.web.controller;

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

import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.form.MePasswordForm;
import medicallab.web.form.MeSettingsForm;
import medicallab.web.model.User;
import medicallab.web.model.service.UserService;

@Controller
@RequestMapping("/me")
public class MeController {
	
	@Autowired private UserService userService;
	@Autowired private MePasswordForm mePasswordForm;
	@Autowired private MeSettingsForm meSettingsForm;
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
	
	
	@GetMapping(params = {"cancel"})
	public String cancel() {
		return "redirect:/";
	}
	
	
	@PostMapping(params = {"meChangeSettings"})
	public String meChangeSettings(Model model,
			Authentication authentication, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("meSettingsForm") MeSettingsForm meSettingsForm,
			Errors errors) {
		
		try {
			userService.updateUserByForm(authentication.getName(), meSettingsForm, errors);
			
			if (errors.hasErrors()) {
				model.addAttribute("mePasswordForm", mePasswordForm);
				return "me";
			}
			
			redirectAttributes.addFlashAttribute("success", 
					messageSource.getMessage("changeSettings.success", null, enLocale));

		} catch(NoUpdatedFieldsException e) {
			redirectAttributes.addFlashAttribute("warning", 
					messageSource.getMessage("changeSettings.warning", null, enLocale));
		}
		
		return "redirect:/me";
	}
	
	
	@PostMapping(params = {"meChangePassword"})
	public String meChangePassword(Authentication authentication,
			Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("mePasswordForm") MePasswordForm mePasswordForm,
			Errors errors) {
						
		try {
			userService.updateUserByForm(authentication.getName(), mePasswordForm, errors);
		} catch (NoUpdatedFieldsException e) {
			// Currently, there is no need to handle this case
		}
		
		if (errors.hasErrors()) {
			User user = userService.findByUsername(authentication.getName());
			meSettingsForm.populateFromObject(user);
			model.addAttribute("meSettingsForm", meSettingsForm);
			
			return "me";
		}
		
		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("changePassword.success", null, enLocale));
		
		return "redirect:/me";
	}
}
