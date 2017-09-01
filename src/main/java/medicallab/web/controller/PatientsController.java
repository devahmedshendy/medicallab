package medicallab.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import medicallab.web.exception.NoSuchPatientException;
import medicallab.web.exception.NoUpdatedFieldsException;
import medicallab.web.form.AddPatientForm;
import medicallab.web.form.EditPatientSettingsForm;
import medicallab.web.form.SearchPatientForm;
import medicallab.web.misc.PagedListHolder;
import medicallab.web.model.Patient;
import medicallab.web.model.service.PatientService;

@Controller
@RequestMapping(value = { "/patients" })
public class PatientsController {
	
	@Autowired private PatientService patientService;
	@Autowired private AddPatientForm addPatientForm;
	@Autowired private EditPatientSettingsForm editPatientSettingsForm;
	@Autowired PagedListHolder<Patient> pagedListHolder;
	@Autowired private MessageSource messageSource;
	
	private final Locale enLocale = new Locale("en");
	
	@Secured(value = { "ROLE_ROOT", "ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_OFFICER" })
	@RequestMapping(value = { "", "search" })
	public String patients(Model model,
			@ModelAttribute("searchPatientForm") SearchPatientForm searchPatientForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "searchGender", required=false) String searchGender,
			@RequestParam(value = "sortField", required=false) String sortField,
			@RequestParam(value = "sortOrder", required=false) String sortOrder,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		Integer currentPage = page;
		Integer nextPage    = currentPage + 1;
		Integer prevPage    = currentPage - 1;
		Integer firstResult = (page - 1) * 10;
		Integer maxResult = 10;
		
		Map<String, String> search = new HashMap<>();
		Map<String, String> sort = new HashMap<>();
		Map<String, String> requestParams = new LinkedHashMap<>();
		Map<String, String> uriListHolder = new HashMap<String, String>();
		
		if (sortField == null && sortOrder == null) {
			sort.put("sortField", "updatedAt");
			sort.put("sortOrder", "DESC");
			
		} else {
			sort.put("sortField", sortField);
			sort.put("sortOrder", "ASC".equals(sortOrder) ? "DESC" : "ASC");
			
			requestParams.put("sortField", sortField);
			requestParams.put("sortOrder", sortOrder);
		}
		
		
		if (searchField != null && searchText != null) {
			search.put("searchField", searchField);
			search.put("searchText", searchText);
			
			requestParams.put("searchField", searchField);
			requestParams.put("searchText", searchText);
		}
		
		if (! "Male".equals(searchGender) && ! "Female".equals(searchGender)) {
			search.put("searchGender", "");
			
			requestParams.put("searchGender", "");
		
		} else if ("Male".equals(searchGender) || "Female".equals(searchGender)) {
			search.put("searchGender", searchGender);
			
			requestParams.put("searchGender", searchGender);
		}
		
		pagedListHolder = patientService.findPagedList(firstResult, maxResult, search, sort);
		uriListHolder.put("sortFields", "fullname patientId phone age createdAt updatedAt");
		
		model.addAttribute("patients", pagedListHolder.getPagedList());
		model.addAttribute("pageSize", pagedListHolder.getPageSize());
		model.addAttribute("totalPatients", pagedListHolder.getNoOfElements());
		model.addAttribute("currentPage", page);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("isLastPage", pagedListHolder.isLastPage());
		model.addAttribute("isFirstPage", pagedListHolder.isFirstPage());

		model.addAttribute("sortField", sort.get("sortField"));
		model.addAttribute("sortOrder", sort.get("sortOrder"));

		model.addAttribute("patientSearchFieldList", patientService.getPatientSearchFieldList());
		model.addAttribute("genderList", patientService.getGenderList());
		model.addAttribute("searchPatientForm", searchPatientForm);
		model.addAttribute("requestParams", requestParams);
		model.addAttribute("uriListHolder", uriListHolder);
		
		return "patients";
	}
	
	
	@Secured("ROLE_OFFICER")
	@RequestMapping(value= { "new", "edit/{patientname}" }, params = { "cancel" })
	public String cancel() {
		return "redirect:/patients";
	}
	
	
	
	@Secured("ROLE_OFFICER")
	@GetMapping(path = "edit/{patientId}")
	public String editPatient(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId) throws NoSuchPatientException {
		
		Patient patient = patientService.findByPatientId(patientId);
		
		editPatientSettingsForm.populateFromObject(patient);
		
		model.addAttribute("patientId", patientId);
		model.addAttribute("editPatientSettingsForm", editPatientSettingsForm);
		model.addAttribute("availableGender", patientService.getGenderList());

		return "editPatient";
	}
	
	@Secured("ROLE_OFFICER")
	@PostMapping(path = "edit/{patientId}", params = { "changePatientSettings" })
	public String updatePatientSettings(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId,
			@ModelAttribute("editPatientSettingsForm") EditPatientSettingsForm editPatientSettingsForm,
			Errors errors) throws NoUpdatedFieldsException, NoSuchPatientException, IOException {
		
		Patient patient =  new Patient();
		
		patient = patientService.updatePatientByForm(patientId, editPatientSettingsForm, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("availableGender", patientService.getGenderList());
			
			return "editPatient";
		}
		
		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("changeSettings.success", null, enLocale));
			
		
		return "redirect:/patients/edit/" + patient.getPatientId();
	}
	

	
	@Secured("ROLE_OFFICER")
	@GetMapping(path = "new")
	public String newPatient(Model model) {
		model.addAttribute("addPatientForm", addPatientForm);
		model.addAttribute("availableGender", patientService.getGenderList());

		return "newPatient";
	}
	
	@Secured("ROLE_OFFICER")
	@PostMapping(path = "new", params = { "addPatient" })
	public String addPatient(Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("addPatientForm") AddPatientForm addPatientForm, 
			Errors errors) throws IOException {
		
		patientService.createNewPatientByForm(addPatientForm, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("availableGender", patientService.getGenderList());

			return "newPatient";
		}

		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("addPatient.success", null, enLocale));

		return "redirect:/patients";
	}
	
	
	@GetMapping(path = "{patientId}/profile-image.png")
	public ResponseEntity<byte[]> getPatientProfileImage(@PathVariable("patientId") String patientId) throws IOException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		
		HttpStatus httpStatus = HttpStatus.OK;

//		InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		
		byte[] patientProfileImage = patientService.findProfileImageByPatientId(patientId); 
		
		if (patientProfileImage == null) {
			httpStatus = HttpStatus.NOT_FOUND;
		}
		
		ResponseEntity<byte[]> profileImageResponseEntity = new ResponseEntity<>(patientProfileImage, headers, httpStatus);
		
		return profileImageResponseEntity;
	}
	
	
	
	@Secured("ROLE_OFFICER")
	@PostMapping(path = "{patientId}", params= { "delete" })
	public String deletePatient(RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId,
			@RequestParam("delete") boolean delete) throws NoSuchPatientException {
		
		if (delete) {
			patientService.deleteByPatientId(patientId);
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("deletePatient.success", new String[] { patientId.toString() }, enLocale));
		}
		
		return "redirect:/patients";
	}
	
	
	
	@GetMapping(path = "medical-profile/{patientId}")
	public String patientMedicalProfile(Model model, @PathVariable("patientId") String patientId) throws NoSuchPatientException {
		
		Patient patient = patientService.findByPatientId(patientId);
		
		model.addAttribute("patient", patient);
		return "medicalProfile";
	}
	
	
	/*
	 * "/patients/api/{patientId}"
	 */
	
	@GetMapping( path = { "api/{patientId}" }, produces = "application/json")
	public ResponseEntity<Patient> apiGetPatient(HttpServletResponse response, @PathVariable("patientId") String patientId) {
		Patient patient = null;
		
		try {
			patient = patientService.findByPatientId(patientId);
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
			
		} catch (NoSuchPatientException e) {
			return new ResponseEntity<Patient>(patient, HttpStatus.NOT_FOUND);
		}
	}
}
