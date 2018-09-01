package medicallab.enums;

public enum TestTypeEnum {
	CBC 			("Complete Blood Count"),
	FMT			("Fake Medical Test"),
	UMT			("Unknow Medical Test");
	
	private String value;
	
	TestTypeEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
