package medicallab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login", method=GET)
	public String login(Principal principal, 
			RedirectAttributes redirectAttributes) {

		return principal == null ? "login" : "redirect:/";
	}
}
