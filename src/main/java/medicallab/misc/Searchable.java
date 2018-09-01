package medicallab.misc;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Searchable {

	private String searchField;
	private String searchText;
	private List<String> medicalTestType;
	private List<String> medicalRequestStatus;
	
}
