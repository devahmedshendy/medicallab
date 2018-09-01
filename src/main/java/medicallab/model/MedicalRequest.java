package medicallab.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.EnumUtils;

import lombok.Getter;
import lombok.Setter;
import medicallab.enums.MedicalRequestStatus;

@Getter @Setter
@Entity(name = "medical_request")
public class MedicalRequest implements Serializable {

	private static final long serialVersionUID = -7322005464248416256L;

	@Id @Column(name = "code", nullable = false)
	private String code = new SimpleDateFormat("yyMMddhhmmss").format(new Date());
	
	@Column(name = "status", nullable = false)
	private String status = MedicalRequestStatus.WAITING.toString();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "officer_id", referencedColumnName = "id", nullable = false)
	private User officer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	private User doctor;
	
	@Column(name = "doctor_comment", nullable = true)
	private String doctorComment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
	private MedicalTest medicalTest;
	
	@Convert(converter = MedicalTestResultJpaConverter.class)
	@Column(name = "test_result", columnDefinition = "json", nullable = false)
	private Map<String, Double> testResult;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
	private Date createdAt = new Date();
	
	@Column(name = "updated_at", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
	private Date updatedAt = new Date();
	
	public static List<String> createRequestStatusList() {
		List<String> requestStatusList = new ArrayList<>();
		
		for (MedicalRequestStatus requestStatus : EnumUtils.getEnumList(MedicalRequestStatus.class)) {
			requestStatusList.add(requestStatus.toString());
		}
		
		return requestStatusList;
	}
	
	public static Map<String, String> createRequestStatusMap() {
		Map<String, String> requestStatusMap = new LinkedHashMap<>();
		
		for (MedicalRequestStatus requestStatus : EnumUtils.getEnumList(MedicalRequestStatus.class)) {
			requestStatusMap.put(requestStatus.toString(), requestStatus.getCamelCaseValue());
		}
		
		return requestStatusMap;
	}
}
