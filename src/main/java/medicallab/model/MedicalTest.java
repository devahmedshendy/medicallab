package medicallab.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "medical_test")
public class MedicalTest implements Serializable {
	
	private static final long serialVersionUID = -5745387583808860424L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "short_name", nullable = false)
	private String shortName;
	
	@Column(name = "long_name", nullable = false)
	private String longName;
	
	@Column(name = "items", columnDefinition = "json", nullable = false)
	@Convert(converter = MedicalTestItemsJpaConverter.class)
	private List<String> items;
	
}
