package medicallab.flow.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestUMT implements Serializable {
	private static final long serialVersionUID = 6146956139658568989L;
	
	private Float abc;
	private Float def;
	private Float ghi;
	private Float jkl;
}
