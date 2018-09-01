package medicallab.flow.request.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestUMT;

@Component
public class RequestUMTValidator {

	public void validateUmtDetailsStep(RequestUMT requestUMT, Errors errors) {
		if (requestUMT.getAbc() == null || !( requestUMT.getAbc() <= 5.0 && requestUMT.getAbc() >= 1.0 ) ) {
			errors.reject("ABCInvalidValue");
		}
		
		if (requestUMT.getDef() == null || !( requestUMT.getDef() <= 13.2 && requestUMT.getDef() >= 5.50 ) ){
			errors.reject("DEFInvalidValue");
		}
		
		if (requestUMT.getGhi() == null || !( requestUMT.getGhi() <= 10.0 && requestUMT.getGhi() >= 8.0 ) ) {
			errors.reject("GHIInvalidValue");
		}
		
		if (requestUMT.getJkl() == null || !( requestUMT.getJkl() <= 20.0 && requestUMT.getJkl() >= 19.0 ) ) {
			errors.reject("JKLInvalidValue");
		}
	}
}
