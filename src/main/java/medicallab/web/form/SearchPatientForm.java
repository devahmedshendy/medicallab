package medicallab.web.form;

import org.springframework.stereotype.Component;

@Component("searchPatientForm")
public class SearchPatientForm {
	private String searchField;
	private String searchText;
	private String searchGender;
	
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

	public String getSearchGender() {
		return searchGender;
	}

	public void setSearchGender(String searchGender) {
		this.searchGender = searchGender;
	}
}
