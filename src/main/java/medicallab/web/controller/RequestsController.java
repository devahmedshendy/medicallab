package medicallab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class RequestsController {
	@RequestMapping(value="/requests", method=GET)
	public String requests() {
		return "requests";
	}
}
