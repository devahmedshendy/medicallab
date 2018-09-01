package medicallab.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "request")
public class Request implements Serializable {
	private static final long serialVersionUID = 4294256465182076353L;

	@Id @Column(name = "request_code", nullable = false)
	private String requestCode;

	@Column(name = "request_status", nullable = false)
	private String requestStatus;

	@Column(name = "test_type", nullable = false)
	private String testType;
	
	@Column(name = "patient_fullname", nullable = false)
	private String patientFullname;
	
	@Column(name = "patient_id", nullable = false)
	private String patientId;
	
	@Column(name = "doctor_fullname", nullable = false)
	private String doctorFullname;
	
	@Column(name = "created_at", nullable = false)
	private Date createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;
}
