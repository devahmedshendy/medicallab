package medicallab.enums;

public enum RequestsPageSearchField {
	PATIENT_FULLNAME		("Patient Full Name"),
	PATIENT_ID			("Patient Id"),
	DOCTOR_FULLNAME		("Doctor Full Name"),
	REQUEST_CODE			("Request Code");
	
	private String value;
	
	RequestsPageSearchField(String value) {
		this.value	= value;
	}
	
	public String getValue() {
		return this.value;
	}
}
