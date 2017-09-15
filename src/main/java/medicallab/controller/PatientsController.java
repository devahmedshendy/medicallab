package medicallab.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
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

import medicallab.form.AddPatientForm;
import medicallab.form.AddPatientFormValidator;
import medicallab.form.PatientSettingsForm;
import medicallab.form.PatientSettingsFormValidator;
import medicallab.form.SearchPatientForm;
import medicallab.misc.Uri;
import medicallab.model.Patient;
import medicallab.model.PatientService;


@Controller
@RequestMapping("/patients")
public class PatientsController {
	
	private final Uri uri;
	private final MessageSource messageSource;
	private final PatientService patientService;
	private final AddPatientForm addPatientForm;
	private final AddPatientFormValidator addPatientFormValidator;
	private final PatientSettingsForm patientSettingsForm;
	private final PatientSettingsFormValidator patientSettingsFormValidator;
	
	private final Locale enLocale = new Locale("en");
	
	
	public PatientsController(Uri uri,
			MessageSource messageSource,
			PatientService patientService,
			AddPatientForm addPatientForm,
			AddPatientFormValidator addPatientFormValidator,
			PatientSettingsForm patientSettingsForm,
			PatientSettingsFormValidator patientSettingsFormValidator) {
		
		this.uri								= uri;
		this.messageSource					= messageSource;
		this.patientService 					= patientService;
		this.addPatientForm 					= addPatientForm;
		this.addPatientFormValidator			= addPatientFormValidator;
		this.patientSettingsForm 			= patientSettingsForm;
		this.patientSettingsFormValidator 	= patientSettingsFormValidator;
	}
	

	/*
	 * Sort/Page/Search Patients
	 */
	@Secured(value = { "ROLE_ROOT", "ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_OFFICER" })
	@RequestMapping(value = { "", "search" })
	public String patients(Model model,
			HttpServletRequest request,
			@ModelAttribute("searchPatientForm") SearchPatientForm searchPatientForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "genderType", required=false) String genderType,
			@RequestParam(value = "sortField", required=false) String sortField,
			@RequestParam(value = "sortOrder", required=false) String sortOrder,
			@RequestParam(value = "maxResult", defaultValue = "10") Integer maxResult,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("page", page.toString());
		queryParams.put("maxResult", maxResult.toString());
		queryParams.put("sortField", sortField);
		queryParams.put("sortOrder", sortOrder);
		queryParams.put("searchField", searchField);
		queryParams.put("searchText", searchText);
		queryParams.put("genderType", genderType);
		
		
		Page<Patient> patientsPagedList = patientService.findPagedList(queryParams);
		
		uri.setRequestUri(request.getRequestURI());
		uri.setQueryParams(queryParams);
		
		model.addAttribute("patientsPage", patientsPagedList);
		model.addAttribute("pageSize", patientsPagedList.getNumberOfElements());
		model.addAttribute("totalPatients", patientsPagedList.getTotalElements());
		model.addAttribute("page", page);
		
		model.addAttribute("patientSearchFieldList", patientService.getPatientSearchFieldList());
		model.addAttribute("genderList", patientService.getGenderList());

		model.addAttribute("sortField", sortField == null ? "updatedAt" : sortField);
		model.addAttribute("sortOrder", sortOrder == null ? "DESC" : sortOrder);

		model.addAttribute("searchPatientForm", searchPatientForm);
		
		return "patients";
	}
	
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@RequestMapping(value= { "new", "edit/{patientname}" }, params = { "cancel" })
	public String cancel() {
		return "redirect:" + uri.get("patients");
	}
	
	/*
	 * Edit Patient Settings
	 */
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@GetMapping(path = "edit/{patientId}")
	public String editPatient(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId) {
		
		Patient patient = patientService.findByPatientId(patientId);
		
		if (patient == null) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("NoSuchPatient", new String[] { patientId }, enLocale));
			
			return "redirect:" + uri.get("patients");
		}
		
		patientService.populateFormFromObject(patientSettingsForm, patient);
		
		model.addAttribute("patientId", patientId);
		model.addAttribute("patientSettingsForm", patientSettingsForm);
		model.addAttribute("genderList", patientService.getGenderList());

		return "edit-patient";
	}
	
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@PostMapping(path = "edit/{patientId}", params = { "updatePatientSettings" })
	public String updatePatientSettings(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId,
			@ModelAttribute("patientSettingsForm") PatientSettingsForm patientSettingsForm,
			Errors errors) {
		
		
		Patient patient =  patientService.findByPatientId(patientId);
		
		if (patient == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchPatient", new String[] { patientId }, enLocale));
		
		} else {
			if (! patientSettingsForm.anyNewUpdates(patient)) {
				redirectAttributes.addFlashAttribute("warning", 
						messageSource.getMessage("UpdateSettingsWarning", null, enLocale));
				
			} else {
				patientSettingsFormValidator.validate(patientSettingsForm, errors);
				
				if (errors.hasErrors()) {
					model.addAttribute("genderList", patientService.getGenderList());
					
					return "edit-patient";
				}
				
				patientService.populateFormIntoObject(patientSettingsForm, patient);
				
				patientService.update(patient);
				redirectAttributes.addFlashAttribute("success", 
						messageSource.getMessage("UpdatePatientSettingsDone", null, enLocale));
			}
		}
		
		return "redirect:" + uri.get("editPatient", patient.getPatientId());
	}
	
	/*
	 * Add Patient
	 */
	@Secured("ROLE_OFFICER")
	@GetMapping(path = "new")
	public String addPatient(Model model) {
		model.addAttribute("addPatientForm", addPatientForm);
		model.addAttribute("genderList", patientService.getGenderList());

		return "add-patient";
	}
	
	@Secured("ROLE_OFFICER")
	@PostMapping(path = "new", params = { "addPatient" })
	public String addPatient(Model model, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("addPatientForm") AddPatientForm addPatientForm, 
			Errors errors) {
		

		addPatientFormValidator.validate(addPatientForm, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("genderList", patientService.getGenderList());

			return "add-patient";
		}

		Patient patient = new Patient();
		patientService.populateFormIntoObject(addPatientForm, patient);
		patientService.create(patient);

		redirectAttributes.addFlashAttribute("success", 
				messageSource.getMessage("AddPatientDone", null, enLocale));

		return "redirect:" + uri.get("patients");
	}
	
	/*
	 * Delete Patient
	 */
	@Secured("ROLE_OFFICER")
	@PostMapping(path = "edit/{patientId}", params= { "delete" })
	public String deletePatient(RedirectAttributes redirectAttributes,
			@PathVariable("patientId") String patientId) {
		
		Patient patient = patientService.findByPatientId(patientId);
		
		if (patient == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchPatient", new String[] { patientId }, enLocale));
			
		} else {
			patientService.delete(patient);
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("DeletePatientDone", new String[] { patientId }, enLocale));
		}
		
		return "redirect:" + uri.get("patients");
	}
	
	/*
	 * Patient Profile Image
	 */
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@GetMapping(path = "profile-image/{patientId}")
	public ResponseEntity<byte[]> getProfileImage(@PathVariable("patientId") String patientId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		headers.add("Content-Type", "image/png");
		
		byte[] profileImage = patientService.findProfileImageByPatientId(patientId);
		
		HttpStatus httpStatus = profileImage == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		
		ResponseEntity<byte[]> responseEntityOfProfileImage = 
				new ResponseEntity<>(profileImage, headers, httpStatus);  
		
		return responseEntityOfProfileImage;
	}
}
