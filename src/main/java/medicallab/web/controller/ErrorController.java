package medicallab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping(path = "/404")
	public String notFound404() {
		return "404";
	}
	
	@RequestMapping(path = "/403")
	public String accessDenied402() {
		return "403";
	}
	
}
