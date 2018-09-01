package medicallab.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import medicallab.enums.RequestStatusEnum;

@Data
@Entity(name = "umt")
public class UMT implements Serializable {
	private static final long serialVersionUID = 5194434767694714011L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Float abc;
	
	@Column(nullable = false)
	private Float def;
	
	@Column(nullable = false)
	private Float ghi;
	
	@Column(nullable = false)
	private Float jkl;
	
	@Column(name = "request_code", nullable = false)
	private String requestCode = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	
	@Column(name = "request_status", nullable = false)
	private String requestStatus = RequestStatusEnum.PENDING.getValue();
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
	private Patient patient;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	private User doctor;
	
	@Column(name = "doctor_comment")
	private String doctorComment;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
	private Date createdAt = new Date();
	
	@Column(name = "updated_at", nullable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
	private Date updatedAt = new Date();
}
