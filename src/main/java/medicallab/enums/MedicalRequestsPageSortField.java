package medicallab.enums;

public enum MedicalRequestsPageSortField {
	UPDATED_AT				("updatedAt", "Date"),
	MEDICAL_TEST_TYPE		("medicalTestType", "Test Type"),
	DOCTOR_FULL_NAME			("doctorFullname", "Doctor Full Name"),
	MEDICAL_REQUEST_CODE		("medicalRequestCode", "Request Code"),
	PATIENT_FULL_NAME		("patientFullname", "Patient Full Name"),
	PATIENT_ID				("patientId", "Patient Id");
	
	private String fieldName;
	
	MedicalRequestsPageSortField(String dbFieldName, String fieldName) {
		this.fieldName = dbFieldName;
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
}
