package medicallab.model.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import medicallab.flow.request.RequestDetails;
import medicallab.misc.Searchable;
import medicallab.model.DoctorReviewDetails;
import medicallab.model.MedicalRequestView;
import medicallab.model.Request;

public interface MedicalRequestsService extends GenericService<Request> {

	Request createRequestFromRequestDetails(RequestDetails requestDetails);

	Map<String, String> findSearchFieldList();

	Request findByRequestCode(String requestCode);

	RequestDetails findRequestDetails(String requestCode);

	void deleteRequest(Request request);

	void createRequestDetails(RequestDetails requestDetails);

	void updateRequestDetails(RequestDetails requestDetails);
	
	DoctorReviewDetails updateDoctorReview(DoctorReviewDetails approval);

	Page<MedicalRequestView> findAll(Pageable pageable);

	Page<MedicalRequestView> filter(Pageable pageable, Searchable searchable);

	Page<MedicalRequestView> findAllByDoctorUsername(String doctorUsername, Pageable pageable);
	
	Page<MedicalRequestView> filterByDoctorUsername(String doctorUsername, Pageable pageable, Searchable searchable);
}
