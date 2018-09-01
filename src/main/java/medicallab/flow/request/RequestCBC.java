package medicallab.flow.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestCBC implements Serializable {
	private static final long serialVersionUID = 4503471661225636240L;
	
	private Float wcb;
	private Float hgb;
	private Float mcv;
	private Float mch;
}
