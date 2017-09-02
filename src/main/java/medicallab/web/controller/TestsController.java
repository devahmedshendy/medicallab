package medicallab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class TestsController {
	@RequestMapping(value="/tests", method=GET)
	public String tests() {
		return "tests";
	}
}
