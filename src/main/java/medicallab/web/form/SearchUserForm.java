package medicallab.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("searchUserForm")
public class SearchUserForm {

	private String searchField;
	private String searchText;
	
	public SearchUserForm() {}
	
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
	
	public Map<String, String> getAvailableSearchFieldList() {
		Map<String, String> searchFieldList = new LinkedHashMap<>();
		searchFieldList.put("firstname", "Firstname");
		searchFieldList.put("lastname", "Lastname");
		searchFieldList.put("username", "Username");
		return searchFieldList;
	}
}
