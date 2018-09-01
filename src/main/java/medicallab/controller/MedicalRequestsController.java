package medicallab.controller;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import medicallab.enums.MedicalRequestStatus;
import medicallab.enums.RequestsPageSearchField;
import medicallab.enums.TestTypeEnum;
import medicallab.flow.request.RequestDetails;
import medicallab.form.FilterMedicalRequestForm;
import medicallab.misc.MedicalRequestRequestParams;
import medicallab.misc.Searchable;
import medicallab.misc.Uri;
import medicallab.model.DoctorReviewDetails;
import medicallab.model.MedicalRequest;
import medicallab.model.MedicalRequestView;
import medicallab.model.Request;
import medicallab.model.User;
import medicallab.model.service.MedicalRequestsService;
import medicallab.model.service.MedicalTestService;
import medicallab.model.service.UserService;
import medicallab.use_case.MedicalRequestsUseCases;

@Controller
@RequestMapping("/md-requests")
public class MedicalRequestsController {

	private final Locale enLocale = new Locale("en");
	
	private final Uri uri;
	private final MessageSource messageSource;
	private final MedicalRequestsService medicalRequestsService;
	private final MedicalRequestsUseCases useCases;
	private final UserService userService;
	private MedicalRequestRequestParams requestParams;
	private final MedicalTestService medicalTestService;
	
	@Autowired
	public MedicalRequestsController(Uri uri,
			MessageSource messageSource,
			MedicalRequestsService medicalRequestsService,
			MedicalRequestsUseCases useCases,
			UserService userService,
			MedicalRequestRequestParams requestParams,
			MedicalTestService medicalTestService) {
		this.uri						= uri;
		this.messageSource  			= messageSource;
		this.medicalRequestsService 	= medicalRequestsService;
		this.useCases 				= useCases;
		this.userService 			= userService;
		this.medicalTestService		= medicalTestService;
		this.requestParams			= requestParams;
	}
	
	
	/*
	 * Requests Home Page
	 */
	@Secured(value = { "ROLE_ADMIN", "ROLE_ROOT", "ROLE_OFFICER", "ROLE_DOCTOR" })
	@RequestMapping(value = { "", "search" })
	public String requests(Model model, 
			Authentication auth,
			HttpServletRequest request,
			@ModelAttribute("filterMedicalRequestForm") FilterMedicalRequestForm filterMedicalRequestForm,
			@RequestParam(value = "searchField", required=false) String searchField,
			@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "testType", required=false) List<String> medicalTestType,
			@RequestParam(value = "requestStatus", required=false) List<String> medicalRequestStatus,
			@RequestParam(value = "sortField", required=false) String sortField,
			@RequestParam(value = "sortOrder", required=false) String sortOrder,
			@RequestParam(value = "maxResult", defaultValue = "10") int maxResult,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		

		Sort sort = createSort(sortField, sortOrder);
		Pageable pageable = new PageRequest(page - 1, maxResult, sort);
		
		Searchable searchable = new Searchable(searchField == null ? "" : searchField,
				searchText == null ? "" : searchText.trim(),
				medicalTestType,
				medicalRequestStatus);
		
		
		Page<MedicalRequestView> medicalRequestsPagedList;
		
		if (currentUserHasDoctorAuthority(auth.getAuthorities())) {
			String currentDoctorUsername = auth.getName();
			medicalRequestsPagedList = searchable.getSearchField().isEmpty() ?
				useCases.getPagedListByDoctorUsername(currentDoctorUsername, pageable) : 
				useCases.getFilteredPagedListByDoctorUsername(currentDoctorUsername, pageable, searchable);
			
		} else {
			medicalRequestsPagedList = searchable.getSearchField().isEmpty() ?
					useCases.getPagedList(pageable) : 
					useCases.getFilteredPagedList(pageable, searchable);
		}
		
		
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("page", Integer.toString(page));
		queryParams.put("maxResult", Integer.toString(maxResult));
		queryParams.put("sortField", sortField);
		queryParams.put("sortOrder", sortOrder);
		queryParams.put("searchField", searchField);
		queryParams.put("searchText", searchText);
		queryParams.put("testType", StringUtils.join(medicalTestType, ","));
		queryParams.put("requestStatus", StringUtils.join(medicalRequestStatus, ","));
		
		
//		Page<Request> requestsPagedList = medicalRequestsService.findPagedList(queryParams);
//		Page<MedicalRequest> medicalRequestsPagedList = useCases.getPagedList(requestParams);
		
		uri.setRequestUri(request.getRequestURI());
		uri.setQueryParams(queryParams);
		
		model.addAttribute("sort", sort);
		model.addAttribute("sortField", sortField == null ? "updatedAt" : sortField);
		model.addAttribute("sortOrder", sortOrder == null ? "DESC" : sortOrder);
		
		model.addAttribute("medicalRequestsPage", medicalRequestsPagedList);
		model.addAttribute("pageSize", medicalRequestsPagedList.getNumberOfElements());
		model.addAttribute("totalMedicalRequests", medicalRequestsPagedList.getTotalElements());
		model.addAttribute("page", page);

		model.addAttribute("sortColumnsMap", MedicalRequestView.createSortColumnsMap());
		model.addAttribute("searchColumnsMap", MedicalRequestView.createSearchColumnsMap());
		model.addAttribute("testTypesMap", MedicalRequestView.findAllAndCreateTestTypesMap(this.medicalTestService));
		model.addAttribute("requestStatusMap", MedicalRequest.createRequestStatusMap());
		
		model.addAttribute("filterMedicalRequestForm", filterMedicalRequestForm);
		return "md-requests";
	}
	
	private boolean currentUserHasDoctorAuthority(Collection<? extends GrantedAuthority> authorities) {
		return authorities.contains(new SimpleGrantedAuthority("ROLE_DOCTOR"));
	}
	
	private Sort createSort(String sortField, String sortOrder) {
		Sort sort = new Sort(Direction.DESC, "updatedAt");

		if (sortField != null && sortOrder != null) {
			Direction sortDirection = "ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC;
			
			switch(sortField) {
				case "patientFullname":
					sort = new Sort(sortDirection, "patientFullname");
					break;
					
				case "patientId":
					sort = new Sort(sortDirection, "patientId");
					break;
					
				case "doctorFullname":
					sort = new Sort(sortDirection, "doctorFullname");
					break;
					
				case "requestCode":
					sort = new Sort(sortDirection, "requestCode");
					break;
					
				case "testType":
					sort = new Sort(sortDirection, "testType");
					break;
					
				case "updatedAt":
					sort = new Sort(sortDirection, "updatedAt");
					break;
			}
		}
		
		return sort;
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
