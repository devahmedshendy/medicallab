package medicallab.web.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.form.AddPatientForm;
import medicallab.web.form.EditPatientSettingsForm;
import medicallab.web.form.validator.AddPatientFormValidator;
import medicallab.web.form.validator.EditPatientSettingsFormValidator;
import medicallab.web.misc.PagedListHolder;
import medicallab.web.model.dao.PatientDAO;
import medicallab.web.model.service.PatientService;

@Transactional
@Service("patientService")
public class PatientServiceImpl implements PatientService {

	@Autowired private PatientDAO patientDAO;
	@Autowired private AddPatientFormValidator addPatientFormValidator;
	@Autowired private EditPatientSettingsFormValidator editPatientSettingsFormValidator;
	@Autowired private PagedListHolder<Patient> pagedListHolder;

	@Override
	public void create(Patient patient) {
		patientDAO.create(patient);
	}

	@Override
	public List<Patient> findAll() {
		return patientDAO.findAll();
	}
	
	@Override
	public Patient findById(Object patientId) {
		return patientDAO.findById( (Long) patientId );
	}

	@Override
	public void update(Patient patient) {
		patient.setUpdatedAt(new Date());
		patientDAO.update(patient);
	}

	@Override
	public void delete(Patient patient) {
		patientDAO.delete(patient);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PagedListHolder<Patient> findPagedList(Integer firstResult, Integer maxResult, 
			Map<String,String> search, Map<String,String> sort) {
		String nativeQueryString 	 = "SELECT * FROM Patient ";
		String nativeCountQueryString = "SELECT COUNT(*) FROM Patient ";
		
		String searchField = search.get("searchField");
		String searchText = search.get("searchText");
		String searchGender = search.get("searchGender");
		
		if ( ! "".equals(searchText) && searchText != null) {
			nativeQueryString += " WHERE Patient." + searchField + " LIKE '" + searchText + "%' ";
			nativeCountQueryString += " WHERE Patient." + searchField + " LIKE '" + searchText + "%' ";
			
			if (! "".equals(searchGender)) {
				nativeQueryString += " AND Patient.gender LIKE '" + searchGender + "' ";
				nativeCountQueryString += " AND Patient.gender LIKE '" + searchGender +"' ";
			}
			
		} else if (! "".equals(searchGender)){
			nativeQueryString += " WHERE Patient.gender LIKE '" + searchGender + "' ";
			nativeCountQueryString += " WHERE Patient.gender LIKE '" + searchGender +"' ";
		}
		
		

		String sortField = sort.get("sortField");
		String sortOrder = sort.get("sortOrder");
		
		nativeQueryString += "ORDER BY " + sortField + " " + sortOrder;
		
		Map<String, Object> result = patientDAO.findPagedList(firstResult, maxResult, nativeQueryString, nativeCountQueryString);
		List<Patient> patientList = (List<Patient>) result.get("pagedList");
		Integer pageSize    = patientList.size();
		Integer noOfElements = (Integer) result.get("noOfElements");
		
		pagedListHolder.setFirstResult(firstResult);
		pagedListHolder.setMaxResult(maxResult);
		pagedListHolder.setPagedList(patientList);
		pagedListHolder.setPageSize(pageSize);
		pagedListHolder.setNoOfElements(noOfElements);
		
		return pagedListHolder;
	}
	
	
	
	@Override
	public Patient findByPatientId(String patientId) throws NoSuchPatientException {
		return patientDAO.findByPatientId(patientId);
	}

	@Override
	public byte[] findProfileImageByPatientId(String patientId) {
		return patientDAO.findProfileImageByPatientId(patientId);
	}
	
	@Override
	public void createNewPatientByForm(Object form, Errors errors) throws IOException {
		Patient patient = new Patient();
		
		if (form instanceof AddPatientForm) {
			AddPatientForm addPatientForm = (AddPatientForm) form;
			
			addPatientFormValidator.validate(addPatientForm, errors);
			
			if (errors.hasErrors()) return;
			
			addPatientForm.populateIntoObject(patient);
		}

		create(patient);
	}
	
	@Override
	public Patient updatePatientByForm(String patientId, Object form, Errors errors) throws NoUpdatedFieldsException, NoSuchPatientException, IOException {
		Patient patient = findByPatientId(patientId);
		
		if (form instanceof EditPatientSettingsForm) {
			EditPatientSettingsForm editPatientSettingsForm = (EditPatientSettingsForm) form;
			
			Map<String, Object> updatedFields = editPatientSettingsForm.getUpdatedFields(patient);
			
			if (updatedFields.size() == 0) {
				throw new NoUpdatedFieldsException();
			}

			editPatientSettingsFormValidator.validate(updatedFields, errors);
			
			if (errors.hasErrors()) return null;
			
			editPatientSettingsForm.populateIntoObject(patient);

			update(patient);
		}
		
		return patient;
	}
	
	@Override
	public void deleteByPatientId(String patientId) throws NoSuchPatientException {
		Patient patient = findByPatientId(patientId);
		
		if (patient != null) delete(patient);
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
