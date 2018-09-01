package medicallab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DoctorReviewDetails {
	private String requestCode;
	private String requestStatus;
	private String testType;
	private String doctorFullname;
	private String doctorComment;
}
