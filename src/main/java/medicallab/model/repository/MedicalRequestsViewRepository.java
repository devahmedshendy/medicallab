package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import medicallab.model.MedicalRequestView;

@Repository
public interface MedicalRequestsViewRepository extends JpaRepository<MedicalRequestView, String>, JpaSpecificationExecutor<MedicalRequestView> {
	
}
