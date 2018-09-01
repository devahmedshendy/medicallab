package medicallab.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import medicallab.misc.Searchable;
import medicallab.model.MedicalRequest;
import medicallab.model.MedicalRequestView;
import medicallab.model.service.MedicalRequestsService;

@SessionScope
@Component
public class MedicalRequestsUseCases {

	private final MedicalRequestsService medicalRequestsService;

	@Autowired
	public MedicalRequestsUseCases(@Qualifier("mySQLMedicalRequestService") MedicalRequestsService service) {
		this.medicalRequestsService = service;
	}

	public Page<MedicalRequestView> getPagedList(Pageable pageable) {
		return medicalRequestsService.findAll(pageable);
	}

	public Page<MedicalRequestView> getFilteredPagedList(Pageable pageable, Searchable searchable) {
		return medicalRequestsService.filter(pageable, searchable);
	}

	public Page<MedicalRequestView> getPagedListByDoctorUsername(String doctorUsername, Pageable pageable) {
		// TODO Auto-generated method stub
		return medicalRequestsService.findAllByDoctorUsername(doctorUsername, pageable);
	}

	public Page<MedicalRequestView> getFilteredPagedListByDoctorUsername(String doctorUsername, Pageable pageable, Searchable searchable) {
		return medicalRequestsService.filterByDoctorUsername(doctorUsername, pageable, searchable);
	}

}
