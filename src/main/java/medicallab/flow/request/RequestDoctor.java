package medicallab.flow.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestDoctor implements Serializable {
	private static final long serialVersionUID = 496749134017455567L;
	
	private Long id;
	private String fullname;
	private String comment;
}
