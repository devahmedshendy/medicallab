package medicallab.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Patient {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 14)
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
	@Column(nullable = false)
	private byte[] profileImage;
	
	@Column(nullable = false)
	private String profileImageName;
	
	@Column(columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false)
	private Date createdAt = new Date();
	
	@Column(columnDefinition = "datetime default CURRENT_TIMESTAMP", nullable = false)
	private Date updatedAt = new Date();
}
