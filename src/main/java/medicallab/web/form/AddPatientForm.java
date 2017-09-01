package medicallab.web.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import medicallab.web.model.Patient;

@Component("addPatientForm")
public class AddPatientForm {
	
	private String fullname;
	private String patientId;
	private String phone;
	private Integer age;
	private String gender;
	private MultipartFile profileImage;
	private String profileImageName;
	
	public AddPatientForm() { }
	
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
	public void setPhone(String phone) {
		this.phone = phone;
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
		
		if (profileImage.getSize() == 0) {
			Resource resource = null;
			File defaultImageFile = null;
			FileInputStream is = null;
			byte[] defaultImageBuffer = null;
					
					
			resource = new ClassPathResource("/images/misc/" + ( "Male".equals(gender) ? "maleDefaultProfileImage.png" : "femaleDefaultProfileImage.png" ));
			defaultImageFile = resource.getFile();
			defaultImageBuffer = new byte[(int) defaultImageFile.length()];
			
			is = new FileInputStream(defaultImageFile);
			is.read(defaultImageBuffer);
			is.close();
			
			patient.setProfileImage(defaultImageBuffer);
			patient.setProfileImageName(defaultImageFile.getName());
			
		} else {
			patient.setProfileImageName(profileImage.getOriginalFilename());
			patient.setProfileImage(profileImage.getBytes());
		}
	}
}
