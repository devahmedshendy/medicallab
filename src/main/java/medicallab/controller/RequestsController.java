package medicallab.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medicallab.enums.RequestsPageSearchField;
import medicallab.enums.RequestStatusEnum;
import medicallab.enums.TestTypeEnum;
import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestDoctor;
import medicallab.flow.request.RequestPatient;
import medicallab.flow.request.RequestDetails.RequestTestType;
import medicallab.form.FilterMedicalRequestForm;
import medicallab.misc.Uri;
import medicallab.model.Request;
import medicallab.model.DoctorReviewDetails;
import medicallab.model.User;
import medicallab.model.projection.DoctorForRequest;
import medicallab.model.service.MedicalRequestsService;
import medicallab.model.service.UserService;

@Controller
@RequestMapping("/requests")
public class RequestsController {
	
	private final Locale enLocale = new Locale("en");
	
	private final Uri uri;
	private final MessageSource messageSource;
	private final MedicalRequestsService medicalRequestsService;
	private final UserService userService;
	
	@Autowired
	public RequestsController(Uri uri,
			MessageSource messageSource,
			MedicalRequestsService medicalRequestsService,
			UserService userService) {
		this.uri					= uri;
		this.messageSource  		= messageSource;
		this.medicalRequestsService 		= medicalRequestsService;
		this.userService 		= userService;
	}
	
	
	/*
	 * Requests Home Page
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT", "ROLE_OFFICER", "ROLE_DOCTOR" })
	@RequestMapping(value = { "", "search" })
	public String requests(Model model, 
			Authentication auth,
			HttpServletRequest request,
			@ModelAttribute("searchRequestForm") FilterMedicalRequestForm filterMedicalRequestForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "testType", required=false) List<String> testType,
			@RequestParam(value = "requestStatus", required=false) List<String> requestStatus,
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
		queryParams.put("testType", StringUtils.join(testType, ","));
		queryParams.put("requestStatus", StringUtils.join(requestStatus, ","));
		
		
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR")) ) {
			queryParams.put("userRole", "ROLE_DOCTOR");
		}
		
		System.out.println("HELLO");
		Page<Request> requestsPagedList = medicalRequestsService.findPagedList(queryParams);
		
		uri.setRequestUri(request.getRequestURI());
		uri.setQueryParams(queryParams);
		
		model.addAttribute("sortField", sortField == null ? "updatedAt" : sortField);
		model.addAttribute("sortOrder", sortOrder == null ? "DESC" : sortOrder);
		
		model.addAttribute("requestsPage", requestsPagedList);
		model.addAttribute("pageSize", requestsPagedList.getNumberOfElements());
		model.addAttribute("totalRequests", requestsPagedList.getTotalElements());
		model.addAttribute("page", page);

		model.addAttribute("requestSearchFieldList", EnumUtils.getEnumList(RequestsPageSearchField.class));
		model.addAttribute("testTypesList", EnumUtils.getEnumList(TestTypeEnum.class));
		model.addAttribute("requestStatusList", EnumUtils.getEnumList(RequestStatusEnum.class));
		
		model.addAttribute("searchRequestForm", filterMedicalRequestForm);
		return "requests";
	}
	
	
	/*
	 * Edit Request Details
	 */
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@GetMapping(path = "edit/{requestCode}")
	public String editUser(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable("requestCode") String requestCode) {
		
		Request request = medicalRequestsService.findByRequestCode(requestCode);
		
		if (request == null) {
			redirectAttributes.addFlashAttribute("error", 
					messageSource.getMessage("NoSuchRequest", new String[] { requestCode }, enLocale));
			
			return "redirect:" + uri.get("requests");
		}
		
		return "redirect:" + uri.get("editRequestFlow", requestCode);
	}
	
	
	/*
	 * Delete Request
	 */
	@Secured(value = { "ROLE_OFFICER", "ROLE_ROOT" })
	@PostMapping(path = "edit/{requestCode}", params= { "delete" })
	public String deleteUser(RedirectAttributes redirectAttributes,
			@PathVariable("requestCode") String requestCode) {
		
		Request request = medicalRequestsService.findByRequestCode(requestCode);
		
		if (request == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("NoSuchRequest", new String[] { requestCode }, enLocale));
			
		} else {
			medicalRequestsService.deleteRequest(request);
			
			redirectAttributes.addFlashAttribute("success",
					messageSource.getMessage("DeleteRequestDone", new String[] { requestCode }, enLocale));
		}
		
		return "redirect:" + uri.get("requests");
	}
	
	
	/*--- API ---*/
	
	// Get Request Details
	@Secured(value = { "ROLE_OFFICER", "ROLE_DOCTOR", "ROLE_ROOT" })
	@GetMapping(path = "api/{requestCode}", produces = "application/json")
	public ResponseEntity<RequestDetails> apiGetRequestDetails(@PathVariable("requestCode") String requestCode) {
		RequestDetails requestDetails = medicalRequestsService.findRequestDetails(requestCode);
		
		if (requestDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}

		return new ResponseEntity<>(requestDetails, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	// Post Doctor Review
	@Secured(value = { "ROLE_DOCTOR" })
	@PostMapping(path = "api/doctor-review", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> apiPostDoctorReview(@RequestBody DoctorReviewDetails doctorReviewDetails) {
		Request request = medicalRequestsService.findByRequestCode(doctorReviewDetails.getRequestCode());
		
		if (request == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		User doctor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (!doctor.getFullname().equals(doctorReviewDetails.getDoctorFullname())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		medicalRequestsService.updateDoctorReview(doctorReviewDetails);
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.TEXT_PLAIN);
		
		return new ResponseEntity<>("success", headers, HttpStatus.OK);
	}
	
}
