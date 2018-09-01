package medicallab.misc;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("uri")
@SessionScope
public class Uri implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String contextRoot	 		= "/";
	
	private String home 					= contextRoot;
	private String me 					= contextRoot + "me";
	
	private String login 				= contextRoot + "login";
	private String logout				= contextRoot + "logout";
	
	private String users 				= contextRoot + "users";
	private String patients 				= contextRoot + "patients";
	private String tests   				= contextRoot + "tests";
	private String requests  			= contextRoot + "requests";
	
	private String logo  				= contextRoot + "images/logo/medicallab-logo.png";
	
	private String defaultImages			= contextRoot + "images";
	
	private String notFound      		= contextRoot + "error/404";
	private String accessDenied      	= contextRoot + "error/403";
	private String internalServerError  = contextRoot + "error/500";
	
	private String projectSourceCodeUrl 	= "https://github.com/devahmedshendy/medicallab";
	
	/*
	 * 
	 */
	private LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
	private String requestUri;
	
	public Uri() {
	}
	
	public String get(String... args) {
		switch (args[0]) {
			case "home":
				return home;

			/*--- Login/Logout Uri ---*/
			case "login":
				return login;
				
			case "logout":
				return logout;
			
			
			/*--- User(s) Uri ---*/
			case "me":
				return me;
				
			case "users":
				return users;
				
			case "searchUsers":
				return users + "/search";

			case "newUser":
				return users + "/new";

			case "editUser":
				return users + "/edit/" + args[1];
				
			case "enableUser":
				return users + "/edit/" + args[1] + "?enable=true";
				
			case "disableUser":
				return users + "/edit/" + args[1] + "?disable=true";
				
			case "deleteUser":
				return users + "/edit/" + args[1] + "?delete=true";
				
			case "apiDoctorList":
				return users + "/api?doctorRole=true";
				
			
			/*--- Patient(s) Uri ---*/
			case "patients":
				return patients;
				
			case "searchPatients":
				return patients + "/search";
				
			case "newPatient":
				return patients + "/new";
				
			case "editPatient":
				return patients + "/edit/" + args[1];
			
			case "deletePatient":
				return patients + "/edit/" + args[1] + "?delete=true";
				
			case "medicalProfile":
				return patients + "/api/medical-profile/" + args[1];
				
			case "patientAsJson":
				return patients + "/api/" + args[1];
				
			case "patient-profile-image":
				return patients + "/api/profile-image/" + args[1];
			
			
			/*--- Test(s) Uri ---*/
			case "tests":
				return tests;
			
			
			/*--- Request(s) Uri ---*/
			case "requests":
				return requests;
				
			case "searchRequests":
				return requests + "/search";
				
			case "newRequest":
				return requests + "/new";
				
			case "editRequest":
				return requests + "/edit/" + args[1];
				
			case "editRequestFlow":
				return requests + "/edit?requestCode=" + args[1];
				
			case "deleteRequest":
				return requests + "/edit/" + args[1] + "?delete=true";
				
			case "apiRequestDetails":
				return requests + "/api/" + args[1];
				
			case "apiPostApproval":
				return requests + "/api/approve";
				
			case "apiPostRejection":
				return requests + "/api/reject";
				
			case "apiPostDoctorReview":
				return requests + "/api/doctor-review";
				
				
				
			
			/*--- Static(s) Resources Uri ---*/
			case "logo":
				return logo;
				
			case "favicon":
				return defaultImages + "/misc/favicon.ico";
				
			case "male-default-profile-image":
				return defaultImages + "/profile-image/male-default-profile-image.png";
				
			case "female-default-profile-image":
				return defaultImages + "/profile-image/female-default-profile-image.png";
				
			case "personal-id-example":
				return defaultImages + "/misc/personal-id-example.png";

			case "project-source-code-url":
				return projectSourceCodeUrl;
			
			
			/*--- Error(s) Uri ---*/
			case "403":
				return accessDenied;
				
			case "404":
				return notFound;
				
			case "500":
				return internalServerError;
				
			
			/*--- Patients Table - Columns Uri ---*/
			case "patientsTable":
				return getPatientColumnSortUri(args[1]);
				
			case "requestsPageTable":
				return getRequestColumnSortUri(args[1]);
				
			case "usersTable":
				return getUserColumnSortUri(args[1]);
				
				
			default:
				return "";
		}
	}
	
	public void setQueryParams(LinkedHashMap<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri; 
	}
	
	public String getUserColumnSortUri(String colName) {
		String columnSortUri = requestUri;
		
		if (queryParams.get("page") != null) {
			columnSortUri += "?page=" + queryParams.get("page");
		}
		
		
		if (queryParams.get("maxResult") != null) {
			columnSortUri += "&maxResult=" + queryParams.get("maxResult");
		}
		
		
		if (queryParams.get("sortField") == null && "updatedAt".equals(colName)) {
			columnSortUri += "&sortField=updatedAt&sortOrder=ASC";
			
		} else if (queryParams.get("sortField") != null && queryParams.get("sortField").equals(colName) ) {
			columnSortUri += String.format("&sortField=%s&sortOrder=%s" , 
											colName, 
											( "ASC".equals(queryParams.get("sortOrder")) ? "DESC" : "ASC" ));
		} else {
			columnSortUri += String.format("&sortField=%s&sortOrder=DESC", colName);
		}
		
		
		if (queryParams.get("searchField") != null) {
			columnSortUri += String.format("&searchField=%s&searchText=%s", 
											queryParams.get("searchField"), 
											queryParams.get("searchText"));
		}
		
		return columnSortUri;
	}
	
	public String getPatientColumnSortUri(String colName) {
		String columnSortUri = requestUri;
		
		if (queryParams.get("page") != null) {
			columnSortUri += "?page=" + queryParams.get("page");
		}
		
		
		if (queryParams.get("maxResult") != null) {
			columnSortUri += "&maxResult=" + queryParams.get("maxResult");
		}
		
		
		if (queryParams.get("sortField") == null && "updatedAt".equals(colName)) {
			columnSortUri += "&sortField=updatedAt&sortOrder=ASC";
			
		} else if (queryParams.get("sortField") != null && queryParams.get("sortField").equals(colName) ) {
			columnSortUri += String.format("&sortField=%s&sortOrder=%s" , 
											colName, 
											( "ASC".equals(queryParams.get("sortOrder")) ? "DESC" : "ASC" ));
		} else {
			columnSortUri += String.format("&sortField=%s&sortOrder=DESC", colName);
		}
		
		
		if (queryParams.get("searchField") != null) {
			columnSortUri += String.format("&searchField=%s&searchText=%s&genderType=%s", 
											queryParams.get("searchField"), 
											queryParams.get("searchText"), 
											queryParams.get("genderType"));
		}
		
		return columnSortUri;
	}
	
	public String getRequestColumnSortUri(String colName) {
		String columnSortUri = requestUri;
		
		if (queryParams.get("page") != null) {
			columnSortUri += "?page=" + queryParams.get("page");
		}
		
		
		if (queryParams.get("maxResult") != null) {
			columnSortUri += "&maxResult=" + queryParams.get("maxResult");
		}
		
		
		if (queryParams.get("sortField") == null && "updatedAt".equals(colName)) {
			columnSortUri += "&sortField=updatedAt&sortOrder=ASC";
			
		} else if (queryParams.get("sortField") != null && queryParams.get("sortField").equals(colName) ) {
			columnSortUri += String.format("&sortField=%s&sortOrder=%s" , 
											colName, 
											( "ASC".equals(queryParams.get("sortOrder")) ? "DESC" : "ASC" ));
		} else {
			columnSortUri += String.format("&sortField=%s&sortOrder=DESC", colName);
		}
		
		
		if (queryParams.get("searchField") != null) {
			columnSortUri += String.format("&searchField=%s&searchText=%s", 
											queryParams.get("searchField"), 
											queryParams.get("searchText"));
		}
		
		if (queryParams.get("testType") != null) {
			for (String testType : Arrays.asList( queryParams.get("testType") )) {
				columnSortUri += String.format("&testType=%s", testType);
			}
		}
		
		if (queryParams.get("requestStatus") != null) {
			for (String testType : Arrays.asList( queryParams.get("requestStatus") )) {
				columnSortUri += String.format("&requestStatus=%s", testType);
			}
		}
		
		return columnSortUri;
	}
	
	public String getPrevPageUri() {
		String prevPageUri = requestUri;
		
		Integer page = Integer.parseInt( queryParams.get("page") ) - 1;
		prevPageUri += "?page=" + page;
		
		for (Map.Entry<String, String> param : queryParams.entrySet()) {
			if (param.getValue() != null && ! "page".equals(param.getKey())) {
				prevPageUri += String.format("&%s=%s", param.getKey(), param.getValue());
			}
		}
		
		return prevPageUri;
	}
	
	public String getNextPageUri() {
		String nextPageUri = requestUri;
		
		Integer page = Integer.parseInt( queryParams.get("page") ) + 1;
		nextPageUri += "?page=" + page;
		
		for (Map.Entry<String, String> param : queryParams.entrySet()) {
			if (param.getValue() != null && ! "page".equals(param.getKey())) {
				nextPageUri += String.format("&%s=%s", param.getKey(), param.getValue());
			}
		}
		
		return nextPageUri;
	}
	
}
