package medicallab.web.misc;

import org.springframework.stereotype.Component;

@Component("uri")
public class Uri {
//	private String contextRoot	 	= "/medicallab/";
	private String contextRoot	 	= "/";
	
	private String home 				= contextRoot;
	private String me 				= contextRoot + "me";
	
	private String login 			= contextRoot + "login";
	private String logout			= contextRoot + "logout";
	
	private String users 			= contextRoot + "users";
	private String patients 			= contextRoot + "patients";
	private String tests   			= contextRoot + "tests";
	private String requests  		= contextRoot + "requests";
	
	private String logo  			= contextRoot + "images/logo/medicallab-logo.png";
	
	private String defaultImages		= contextRoot + "images";
	
	private String notFound      	= contextRoot + "errors/404";
	private String accessDenied      = contextRoot + "errors/403";
	private String internalServerError    = contextRoot + "errors/500";
	
	public Uri() {
	}
	
	public String get(String... args) {
		switch (args[0]) {
			case "home":
				return home;
				
				
			case "me":
				return me;

				
			case "login":
				return login;
				
			case "logout":
				return logout;
			
				
			case "users":
				return users;
				
			case "searchUsers":
				return users + "/search";

			case "newUser":
				return users + "/new";

			case "editUser":
				return users + "/edit/" + args[1];
				
				
			case "patients":
				return patients;
				
			case "searchPatients":
				return patients + "/search";
				
			case "newPatient":
				return patients + "/new";
				
			case "editPatient":
				return String.format(patients + "/edit/%s", args[1]);
				
			case "medicalProfile":
				return String.format(patients + "/medical-profile/%s", args[1]);
				
			case "patientProfileImage":
				return String.format(patients + "/%s/profile-image.png", args[1]); 
			
				
			case "tests":
				return tests;
			
				
			case "requests":
				return requests;
				
				
			case "logo":
				return logo;
				
			case "maleDefaultProfileImage":
				return defaultImages + "/misc/maleDefaultProfileImage.png";
				
			case "femaleDefaultProfileImage":
				return defaultImages + "/misc/femaleDefaultProfileImage.png";
				
			case "personalIdExample":
				return defaultImages + "/misc/personalIdExample.png";
				
				
			case "403":
				return accessDenied;
				
			case "404":
				return notFound;
				
			case "500":
				return internalServerError;
				
			default:
				return "";
		}
	}
	
}
