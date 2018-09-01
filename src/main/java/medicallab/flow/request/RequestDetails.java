package medicallab.flow.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails implements Serializable {
	private static final long serialVersionUID = -9184191144284323348L;
	
	private String 			requestCode;
	private String 			requestStatus;
	private RequestPatient 	requestPatient 	= new RequestPatient();
	private RequestDoctor 	requestDoctor 	= new RequestDoctor();
	private Object			requestTest;
	private RequestTestType 	requestTestType = new RequestTestType();

	
	@Data
	public static class RequestTestType implements Serializable {
		private static final long serialVersionUID = 9082538903467067187L;
		private String selectedTestType;
	}
	
	
	@JsonIgnore
	public RequestCBC getRequestCBC() {
		if ( !(requestTest instanceof RequestCBC) ) {
			requestTest = new RequestCBC(); 
		}
		
		return (RequestCBC) requestTest;
	}
	
	@JsonIgnore
	public RequestFMT getRequestFMT() {
		if ( !(requestTest instanceof RequestFMT) ) {
			requestTest = new RequestFMT(); 
		}
		
		return (RequestFMT) requestTest;
	}
	
	@JsonIgnore
	public RequestUMT getRequestUMT() {
		if ( !(requestTest instanceof RequestUMT) ) {
			requestTest = new RequestUMT(); 
		}
		
		return (RequestUMT) requestTest;
	}
	
	public RequestTestType getRequestTestType() {
		return this.requestTestType;
	}
	
	public void setRequestTestType(RequestTestType requestTestType) {
		this.requestTestType = requestTestType;
	}
}
