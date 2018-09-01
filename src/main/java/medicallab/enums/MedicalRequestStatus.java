package medicallab.enums;

public enum MedicalRequestStatus {
	
	WAITING		("Waiting"), 
	APPROVED		("Approved"), 
	RETURNED		("Returned"),
	DROPPED		("Dropped");

	private String camelCaseValue;
	
	MedicalRequestStatus(String value) {
		this.camelCaseValue = value;
	}
	
	public String getCamelCaseValue() {
		return this.camelCaseValue;
	}
}
