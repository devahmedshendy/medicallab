package medicallab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors/")
public class ErrorController {
	
	@RequestMapping(path = "403")
	public String accessDenied403() {
		return "accessDenied";
	}
	
	@RequestMapping(path = "404")
	public String notFound404(Model model, @ModelAttribute("errorMessage") String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
		return "notFound";
	}
	
	@RequestMapping(path = "500")
	public String internalServerError500() {
		return "noHandlerFound";
	}
	
}
