package medicallab.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainControllerAdvice {
	
	public final Uri uri;
	
	@Autowired
	public MainControllerAdvice(Uri uri) {
		this.uri = uri;
	}
	
	@ModelAttribute
	public void setGlobalModeAttributes(Model model) {
		model.addAttribute("uri", uri);
	}
}
