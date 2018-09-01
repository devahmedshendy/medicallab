package medicallab.flow.request.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import medicallab.flow.request.RequestFMT;

@Component
public class RequestFMTValidator {
	public void validateFmtDetailsStep(RequestFMT requestFMT, Errors errors) {
		if (requestFMT.getMno() == null || !( requestFMT.getMno() <= 7.0 && requestFMT.getMno() >= 2.0 ) ) {
			errors.reject("MNOInvalidValue");
		}
		
		if (requestFMT.getPqr() == null || !( requestFMT.getPqr() <= 7.0 && requestFMT.getPqr() >= 6.2 ) ){
			errors.reject("PQRInvalidValue");
		}
		
		if (requestFMT.getStu() == null || !( requestFMT.getStu() <= 3.75 && requestFMT.getStu() >= 2.0 ) ) {
			errors.reject("STUInvalidValue");
		}
		
		if (requestFMT.getVwx() == null || !( requestFMT.getVwx() <= 35.10 && requestFMT.getVwx() >= 12.4 ) ) {
			errors.reject("VWXInvalidValue");
		}
	}
}
