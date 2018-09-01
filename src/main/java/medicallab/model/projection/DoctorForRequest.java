package medicallab.model.projection;

import org.springframework.data.rest.core.config.Projection;

import medicallab.model.User;

@Projection(name = "doctorForRequest", types = User.class)
public interface DoctorForRequest {
	Long getId();
	String getFullname();
}