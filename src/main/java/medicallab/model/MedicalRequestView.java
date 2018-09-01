package medicallab.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import medicallab.enums.MedicalRequestStatus;
import medicallab.model.service.MedicalTestService;

@Getter @Setter
@Entity(name = "medical_request_view")
public class MedicalRequestView {

	@Id @Column(name = "request_code")
	private String requestCode;
	
	@Column(name = "request_status")
	@Enumerated(EnumType.STRING)
	private MedicalRequestStatus requestStatus;
	
	@Column(name = "officer_fullname")
	private String officerFullname;
	
	@Column(name = "patient_id")
	private String patient_id;
	
	@Column(name = "patient_fullname")
	private String patientFullname;
	
	@Column(name = "doctor_fullname")
	private String doctorFullname;
	
	@Column(name = "test_type")
	private String testType;
	
	@Column(name = "test_result", columnDefinition = "json")
	@Convert(converter = MedicalTestResultJpaConverter.class)
	private Map<String, Double> testResult;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	public static Map<String, String> createSortColumnsMap() {
		Map<String, String> sortColumn = new LinkedHashMap<>();
		
		sortColumn.put("updatedAt", "Date");
		sortColumn.put("testType", "Test Type");
		sortColumn.put("doctorFullname", "Doctor Full Name");
		sortColumn.put("requestCode", "Request Code");
		sortColumn.put("patientFullname", "Patient Full Name");
		sortColumn.put("patientId", "Patient Id");
		
		return sortColumn;
	}
	
	public static Map<String, String> createSearchColumnsMap() {
		Map<String, String> searchColumn = new LinkedHashMap<>();
		
		searchColumn.put("patientFullname", "Patient Full Name");
		searchColumn.put("patientId", "Patient Personal Id");
		searchColumn.put("doctorFullname", "Doctor Full Name");
		searchColumn.put("requestCode", "Request Code");
		
		return searchColumn;
	}
	
	public static Map<String, String> findAllAndCreateTestTypesMap(MedicalTestService service) {
		Map<String, String> testTypeMap = new LinkedHashMap<>();
		
		for (MedicalTest test : service.findAll()) {
			testTypeMap.put(test.getShortName(), test.getLongName());
		}

		return testTypeMap;
	}
}
