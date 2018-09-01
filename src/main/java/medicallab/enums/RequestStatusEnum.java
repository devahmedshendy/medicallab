package medicallab.enums;

public enum RequestStatusEnum {
	PENDING 			("Pending"), 
	APPROVED			("Approved"), 
	RETURNED			("Returned");
	
	private String value;
	
	RequestStatusEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
