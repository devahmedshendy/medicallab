package medicallab.flow.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import medicallab.enums.TestTypeEnum;
import medicallab.model.User;
import medicallab.model.service.MedicalRequestsService;
import medicallab.model.service.UserService;

@Component
@SessionScope
public class NewRequestFlow {
	
	private final UserService userService;
	private final MedicalRequestsService medicalRequestsService;
	
	@Autowired
	public NewRequestFlow(UserService userService,
			MedicalRequestsService medicalRequestsService) {
		this.userService 	= userService;
		this.medicalRequestsService = medicalRequestsService;
	}

	public void printStartMessage() {
		System.out.println("Submit Request Flow started .......");
	}
	
	public void printEndMessage() {
		System.out.println("Submit Request Flow stopped.");
	}
	
	public RequestDetails getNewRequestDetails() {
		RequestDetails requestDetails = new RequestDetails();
//		requestDetails.setRequestPatient(new RequestPatient());
//		requestDetails.setRequestDoctor(new RequestDoctor());
//		requestDetails.setRequestTestType(new RequestTestType());
		return requestDetails;
	}
	
	public List<User> getDoctorList() {
		return userService.findByDoctorRole();
	}
	
	public Map<String, String> getTestTypes() {
		Map<String, String> testEnumTypes = new HashMap<>();
		
		for (TestTypeEnum test : TestTypeEnum.values()) {
			testEnumTypes.put(test.name(), test.getValue());
		}
		
		return testEnumTypes;
	}
	
	public void createRequestDetails(RequestDetails requestDetails) {
		medicalRequestsService.createRequestDetails(requestDetails);
	}
	
	public boolean checkForRequestWithCode(String requestCode) {
		return medicalRequestsService.findByRequestCode(requestCode) == null ? false : true;
	}
}
