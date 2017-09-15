package medicallab.form;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import medicallab.model.Patient;

@Component
@RequestScope
public class PatientSettingsForm {

	private String fullname;
	private String patientId;
	private String phone;
	private Integer age;
	private String gender;
	private MultipartFile profileImage;
	private String profileImageName;
	
	private Map<String, Object> updatedFields = new HashMap<String, Object>();
	
	public PatientSettingsForm() { }
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String patientname) {
		this.phone = patientname;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileImageName() {
		return profileImageName;
	}

	public void setProfileImageName(String profileImageName) {
		this.profileImageName = profileImageName;
	}
	
	public Map<String, Object> getUpdatedFields() {
		return updatedFields;
	}
	
	public boolean anyNewUpdates(Patient patient) {
		if (! patient.getPatientId().equals(patientId)) {
			updatedFields.put("patientId", patientId.toString());
		}
		
		if (! patient.getFullname().equals(fullname)) {
			updatedFields.put("fullname", fullname);
		}
		
		if (! patient.getPhone().equals(phone)) {
			updatedFields.put("phone", phone);
		}
		
		if ((Integer) patient.getAge() != age) {
			updatedFields.put("age", age);
		}
		
		if (! patient.getGender().equals(gender) ) {
			updatedFields.put("gender", gender);
		} 
		
		if (profileImage != null) {
			updatedFields.put("profileImage", profileImage);
		}
		
		return updatedFields.isEmpty() ? false : true;
	}
}
