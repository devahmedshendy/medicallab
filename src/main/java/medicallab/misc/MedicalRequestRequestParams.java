package medicallab.misc;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

@Data
@Component @RequestScope
public class MedicalRequestRequestParams extends ParamsQuery {

	private Searchable searchable;
	private String sortField;
	private String sortOrder;
	private Pageable pageable;
//	private int page;
	private int maxResult;
	private String medicalTestShortName;
	private String medicalRequestStatus;
}
