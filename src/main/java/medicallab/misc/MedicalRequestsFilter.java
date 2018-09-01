package medicallab.misc;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRequestsFilter {

	private Searchable searchable;
	private List<String> medicalTestType;
	private List<String> medicalRequestStatus;
	
}
