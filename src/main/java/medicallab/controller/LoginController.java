package medicallab.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/login")
public class LoginController {
	
	@GetMapping
	public String login(Principal principal) {
		return principal == null ? "login" : "redirect:/";
	}
}
