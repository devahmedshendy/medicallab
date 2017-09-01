package medicallab.web.misc;

import java.util.Locale;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.exception.NoSuchUserException;
import medicallab.web.exception.NoUpdatedFieldsException;

@ControllerAdvice
public class MainControllerAdvice {
	@Autowired private MessageSource messageSource;
	@Autowired private Uri uri;
	
	private final Locale enLocale = new Locale("en");

	
	@ModelAttribute("uri")
	public Uri uri() {
		return uri;
	}
	
	
	@ExceptionHandler(NoSuchPatientException.class)
	public String noSuchPatientExceptionHandler(RedirectAttributes redirectAttributes, NoSuchPatientException e) {
		
		redirectAttributes.addFlashAttribute("errorMessage", 
				messageSource.getMessage("NoSuchPatient", new String[] { e.getMessage() }, enLocale));
		
		return "redirect:" + uri.get("404");
	}
	
	
	@ExceptionHandler(NoSuchUserException.class)
	public String noSuchUserExceptionHandler(RedirectAttributes redirectAttributes, NoSuchUserException e) {
		
		redirectAttributes.addFlashAttribute("errorMessage", 
				messageSource.getMessage("NoSuchUser", new String[] { e.getMessage() }, enLocale));
		
		return "redirect:" + uri.get("404");
	}
	
	
	@ExceptionHandler(NoResultException.class)
	public String noResultExceptionHandler(RedirectAttributes redirectAttributes, NoResultException e) {
		
		redirectAttributes.addFlashAttribute("errorMessage", 
				messageSource.getMessage("NoResultFound", new String[] { e.getMessage() }, enLocale));
		
		return "redirect:" + uri.get("404");
	}
	
	
	@ExceptionHandler(NoUpdatedFieldsException.class)
	public String noUpdatedFieldsExceptionHandler(RedirectAttributes redirectAttributes, NoUpdatedFieldsException e,
			HttpServletRequest request) {
		
		redirectAttributes.addFlashAttribute("warning", 
				messageSource.getMessage("changeSettings.warning", null, enLocale));
		
//		request.getRequestURL();
		return "redirect:" + request.getHeader("Referer");
	}
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String notHandlerFoundExceptionHanlder(Exception e) {
		e.printStackTrace();
		return "noHandlerFound";
	}
	
}
