package medicallab.flow.request.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestDoctor;
import medicallab.model.User;
import medicallab.model.service.UserService;

@Component
public class RequestDoctorValidator {
	private final UserService userService;
	
	@Autowired
	public RequestDoctorValidator(UserService userService) {
		this.userService 	= userService;
	}
	
	public void validateDoctorStep(RequestDoctor requestDoctor, Errors errors) {
		if (requestDoctor.getId() == null) {
			errors.reject("DoctorIsRequired"); 
			return;
		}
		
		User user = userService.findById(requestDoctor.getId());
		
		if (user == null) {
			errors.reject("DoctorIsRequired");
			return;
		}
			
		requestDoctor.setFullname(user.getFullname());
	}
}
