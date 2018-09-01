package medicallab.flow.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestFMT implements Serializable {
	private static final long serialVersionUID = -7817921021661134959L;
	
	private Float mno;
	private Float pqr;
	private Float stu;
	private Float vwx;
}
