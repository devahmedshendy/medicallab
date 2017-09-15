package medicallab.form;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SearchPatientForm {
	private String searchField;
	private String searchText;
	private String genderType;
	
	public SearchPatientForm() {}
	
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getGenderType() {
		return genderType;
	}

	public void setGenderType(String searchGender) {
		this.genderType = searchGender;
	}
}
