package medicallab.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "patient")
public class Patient implements Serializable {
	private static final long serialVersionUID = 8851352320495232399L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "patient_id", nullable = false, unique = true, length = 14)
	private String patientId;
	
	@Column(nullable = false)
	private String fullname;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false)
	private Integer age;
	
	@Lob
	@Column(name = "profile_image", nullable = false)
	private byte[] profileImage;
	
	@Column(name = "profile_image_name", nullable = false)
	private String profileImageName;
	
	@Column(name = "created_at", columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false)
	private Date createdAt = new Date();
	
	@Column(name = "updated_at", columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false)
	private Date updatedAt = new Date();
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
//	private List<CBC> cbcList;
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
//	private List<UMT> umtList;
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
//	private List<FMT> fmtList;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<MedicalRequest> medicalRequest;
}
