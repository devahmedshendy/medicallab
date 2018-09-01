package medicallab.flow.request.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestDetails.RequestTestType;

@Component
public class RequestTestTypeValidator {
	
	public void validateTestTypeStep(RequestTestType requestTestType, Errors errors) {
		if (requestTestType.getSelectedTestType() == null) {
			errors.reject("TestTypeIsRequired");
		}
	}
}
