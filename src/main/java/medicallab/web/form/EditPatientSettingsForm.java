package medicallab.web.form;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import medicallab.web.model.Patient;

@Component("editPatientSettingsForm")
public class EditPatientSettingsForm {

	private String fullname;
	private String patientId;
	private String phone;
	private Integer age;
	private String gender;
	private MultipartFile profileImage;
	private String profileImageName;
	
	public EditPatientSettingsForm() { }
	
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

	public void populateIntoObject(Patient patient) throws IOException {
		patient.setPatientId(patientId);
		patient.setFullname(fullname);
		patient.setPhone(phone);
		patient.setAge(age);
		patient.setGender(gender);
		
		if (profileImage.getSize() != 0) {
			patient.setProfileImageName(profileImage.getOriginalFilename());
			patient.setProfileImage(profileImage.getBytes());
		}
	}

	public void populateFromObject(Patient patient) {
		patientId  			= patient.getPatientId();
		fullname 			= patient.getFullname();
		phone  				= patient.getPhone();
		age 					= patient.getAge();
		gender    			= patient.getGender();
		profileImageName    	= patient.getProfileImageName();
	}
	
	public HashMap<String, Object> getUpdatedFields(Patient patient) throws IOException {
		HashMap<String, Object> updatedFields = new HashMap<>();
		
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
			
//			String defaultProfileImageName = gender.toLowerCase() + "DefaultProfileImage.png";
//			if ("".equals(patient.getProfileImageName()) || patient.getProfileImageName() == null || ! defaultProfileImageName.equals(patient.getProfileImageName())) {
//				
//			}
		
		} 
		
		if (profileImage.getBytes().length != 0) {
			updatedFields.put("profileImage", profileImage);
		}
		
		return updatedFields;
	}
}
