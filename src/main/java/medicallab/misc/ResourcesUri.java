package medicallab.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Component("resourcesUri")
@SessionScope
public class ResourcesUri {
	
	private String contextRoot	 		= "/";
	private String logo  				= contextRoot + "images/logo/medicallab-logo.png";
	private String defaultImages			= contextRoot + "images";
	private String projectSourceCodeUrl 	= "https://github.com/devahmedshendy/medicallab";
	
	@Autowired
	private ResourceUrlProvider mvcResourceUrlProvider;

	public ResourcesUri(ResourceUrlProvider mvcResourceUrlProvider) {
		this.mvcResourceUrlProvider = mvcResourceUrlProvider;
	}
	
	public String get(String... args) {
		switch (args[0]) {
			/*
			 * Static(s) Resources Uri
			 */
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
				
			case "bootstrap.min.css":
				return mvcResourceUrlProvider.getForLookupPath("/css/lib/bootstrap/4.0.0-beta/bootstrap.min.css");
				
			case "font-awesome.min.css":
				return mvcResourceUrlProvider.getForLookupPath("/css/lib/font-awesome/4.7.0/font-awesome.min.css");
			
			case "animate.min.css":
				return mvcResourceUrlProvider.getForLookupPath("/css/lib/animate.css/3.5.2/animate.min.css");
				
			case "app.css":
				return mvcResourceUrlProvider.getForLookupPath("/css/app.css");
			
			case "jquery-3.2.1.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/jquery/3.2.1/jquery-3.2.1.min.js");
						
						
			case "popper-1.11.0.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/popper.js/1.11.0/popper-1.11.0.min.js");
				
			case "tether.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/tether/1.4.0/tether.min.js");
				
			case "bootstrap.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/bootstrap/4.0.0-beta/bootstrap.min.js");
				
			case "URI.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/urijs/1.18.12/URI.min.js");
				
			case "jquery.URI.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/urijs/1.18.12/jquery.URI.min.js");
				
			case "qwest.min.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/lib/qwest/4.5.0/qwest.min.js");
				
			case "app.js":
				return mvcResourceUrlProvider.getForLookupPath("/js/app.js");
				
			case "project-source-code-url":
				return projectSourceCodeUrl;
				
			default:
				return "";
		}
	}
	
}
