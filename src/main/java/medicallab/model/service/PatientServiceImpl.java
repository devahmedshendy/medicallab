package medicallab.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import medicallab.form.AddPatientForm;
import medicallab.form.PatientSettingsForm;
import medicallab.model.Patient;
import medicallab.model.repository.PatientRepository;
import medicallab.model.specification.PatientSpecifications;

@Service
@Transactional
public class PatientServiceImpl extends PatientSpecifications implements PatientService {

	private final PatientRepository patientRepo;
	
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepo) {
		this.patientRepo = patientRepo;
	}
	
	
	@Override
	public void create(Patient patient) {
		patientRepo.save(patient);
	}

	@Override
	public void update(Patient patient) {
		patient.setUpdatedAt(new Date());
		patientRepo.save(patient);
	}

	@Override
	public void delete(Patient patient) {
		patientRepo.delete(patient);
	}

	@Override
	public Patient findByPatientId(String patientId) {
		return patientRepo.findByPatientId(patientId);
	}
	
	@Override
	public byte[] findProfileImageByPatientId(String patientId) {
		return patientRepo.findProfileImageForPatientId(patientId);
	}

	@Override
	public Page<Patient> findPagedList(Map<String, String> queryParams) throws NumberFormatException {
		Page<Patient> patientsPagedList = null;
		
		String searchField = queryParams.get("searchField") == null ? "" : queryParams.get("searchField");
		String searchText = queryParams.get("searchText") == null ? "" : queryParams.get("searchText").trim();
		
		String genderType = queryParams.get("genderType");
		
		String sortField = queryParams.get("sortField");
		String sortOrder = queryParams.get("sortOrder");
		
		Integer page = Integer.parseInt(queryParams.get("page")) - 1;
		Integer maxResult = Integer.parseInt(queryParams.get("maxResult"));
		
		Sort sort = null;
		if (sortField == null && sortOrder == null) {
			sort = new Sort(Direction.DESC, "updatedAt");

		} else {
			switch(sortField) {
				case "fullname":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "fullname");
					break;
					
				case "patientId":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "patientId");
					break;
					
				case "phone":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "phone");
					break;
					
				case "gender":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "gender");
					break;
					
				case "age":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "age");
					break;
					
				case "createdAt":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "createdAt");
					break;
					
				case "updatedAt":
					sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "updatedAt");
					break;
			}
		}
		
		
		Pageable pageable = new PageRequest(page, maxResult, sort);
		switch(searchField) {
			case "fullname":
				patientsPagedList = patientRepo.findAll(fullnameStartingWithAndGenderTypeIs(searchText, genderType), pageable);
				break;
				
			case "patientId":
				patientsPagedList = patientRepo.findAll(patientIdStartingWithAndGenderTypeIs(searchText, genderType), pageable);
				break;
				
			default:
				patientsPagedList = patientRepo.findAll(pageable);
				break;
		}
		
		return patientsPagedList;
	}

	@Override
	public void populateFormIntoObject(Object obj, Patient patient) {
		if (obj instanceof PatientSettingsForm) {
			PatientSettingsForm patientSettingsForm = (PatientSettingsForm) obj;

			patient.setPatientId(patientSettingsForm.getPatientId());
			patient.setFullname(patientSettingsForm.getFullname());
			patient.setPhone(patientSettingsForm.getPhone());
			patient.setAge(patientSettingsForm.getAge());
			patient.setGender(patientSettingsForm.getGender());
			
			if (patientSettingsForm.getProfileImage() != null) {
				try {
					patient.setProfileImage(patientSettingsForm.getProfileImage().getBytes());
					patient.setProfileImageName(patientSettingsForm.getProfileImage().getOriginalFilename());
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else if (obj instanceof AddPatientForm) {
			AddPatientForm addPatientForm = (AddPatientForm) obj;
			
			patient.setPatientId(addPatientForm.getPatientId());
			patient.setFullname(addPatientForm.getFullname());
			patient.setPhone(addPatientForm.getPhone());
			patient.setAge(addPatientForm.getAge());
			patient.setGender(addPatientForm.getGender());
		
			if (addPatientForm.getProfileImage().getSize() > 0) {
				try {
					patient.setProfileImageName(addPatientForm.getProfileImage().getOriginalFilename());
					patient.setProfileImage(addPatientForm.getProfileImage().getBytes());
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				patient.setProfileImageName("");
				patient.setProfileImage(new byte[] {});
			}
				
//				if (addPatientForm.getProfileImage().getSize() == 0) {
//					Resource resource = null;
//					File defaultImageFile = null;
//					FileInputStream is = null;
//					byte[] defaultImageBuffer = null;
//					
//					
//					resource = new ClassPathResource("/static/images/profile-image/" + ( "Male".equals(addPatientForm.getGender()) ? "male-default-profile-image.png" : "female-default-profile-image.png" ));
//					defaultImageFile = resource.getFile();
//					defaultImageBuffer = new byte[(int) defaultImageFile.length()];
//					
//					is = new FileInputStream(defaultImageFile);
//					is.read(defaultImageBuffer);
//					is.close();
//					
//					patient.setProfileImage(defaultImageBuffer);
//					patient.setProfileImageName(defaultImageFile.getName());
//					
//				} else {
//					patient.setProfileImageName(addPatientForm.getProfileImage().getOriginalFilename());
//					patient.setProfileImage(addPatientForm.getProfileImage().getBytes());
//				}
				
		}
	}

	@Override
	public void populateFormFromObject(Object obj, Patient patient) {
		if (obj instanceof PatientSettingsForm) {
			PatientSettingsForm patientSettingsForm = (PatientSettingsForm) obj;
			
			patientSettingsForm.setPatientId(patient.getPatientId());
			patientSettingsForm.setFullname(patient.getFullname());
			patientSettingsForm.setPhone(patient.getPhone());
			patientSettingsForm.setAge(patient.getAge());
			patientSettingsForm.setGender(patient.getGender());
			if (patient.getProfileImage().length != 0 ) {
				patientSettingsForm.setProfileImageName(patient.getProfileImageName());
			}
		}
	}

	@Override
	public Map<String, String> getPatientSearchFieldList() {
		Map<String, String> searchFieldList = new LinkedHashMap<>();
		searchFieldList.put("fullname", "Full Name");
		searchFieldList.put("patientId", "Patient Id");
		return searchFieldList;
	}

	@Override
	public List<String> getGenderList() {
		return new ArrayList<String>(Arrays.asList("Male", "Female"));
	}
}
