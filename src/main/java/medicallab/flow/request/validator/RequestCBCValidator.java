package medicallab.flow.request.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestCBC;

@Component
public class RequestCBCValidator {

	public void validateCbcDetailsStep(RequestCBC requestCBC, Errors errors) {
		if (requestCBC.getWcb() == null || !( requestCBC.getWcb() <= 10.10 && requestCBC.getWcb() >= 3.70 ) ) {
			errors.reject("WCBInvalidValue");
		}
		
		if (requestCBC.getHgb() == null || !( requestCBC.getHgb() <= 15.9 && requestCBC.getHgb() >= 12.9 ) ){
			errors.reject("HGBInvalidValue");
		}
		
		if (requestCBC.getMcv() == null || !( requestCBC.getMcv() <= 96.0 && requestCBC.getMcv() >= 81.1 ) ) {
			errors.reject("MCVInvalidValue");
		}
		
		if (requestCBC.getMch() == null || !( requestCBC.getMch() <= 31.2 && requestCBC.getMch() >= 27.0 ) ) {
			errors.reject("MCHInvalidValue");
		}
	}
}
