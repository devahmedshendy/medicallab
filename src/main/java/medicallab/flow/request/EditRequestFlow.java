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
public class EditRequestFlow {
	
	private final UserService userService;
	private final MedicalRequestsService medicalRequestsService;

	@Autowired
	public EditRequestFlow(UserService userService,
			MedicalRequestsService medicalRequestsService) {
		this.userService 	= userService;
		this.medicalRequestsService = medicalRequestsService;
	}

	public void printStartMessage() {
		System.out.println("Edit Request Flow started .......");
	}
	
	public void printEndMessage() {
		System.out.println("Edit Request Flow stopped.");
	}
	
	public RequestDetails getRequestDetails(String requestCode) {
		return medicalRequestsService.findRequestDetails(requestCode);
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
	
	public void updateRequestDetails(RequestDetails requestDetails) {
		medicalRequestsService.updateRequestDetails(requestDetails);
	}
	
	public boolean checkForRequestWithCode(String requestCode) {
		return medicalRequestsService.findByRequestCode(requestCode) == null ? false : true;
	}
}
