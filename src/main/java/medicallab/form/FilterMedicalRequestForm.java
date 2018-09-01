package medicallab.form;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component	@SessionScope
public class FilterMedicalRequestForm {
	private String searchField;
	private String searchText;
	private String[] medicalTestType;
	private String[] medicalRequestStatus;
}
