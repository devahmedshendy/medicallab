package medicallab.flow.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class RequestPatient implements Serializable {
	private static final long serialVersionUID = -8196486977723457723L;
	
	private String patientId;
	private String fullname;
	private String phone;
	private String gender;
	private Integer age;
}