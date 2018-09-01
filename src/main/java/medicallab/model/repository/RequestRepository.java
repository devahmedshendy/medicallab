package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import medicallab.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request>  {
	Request findByPatientId(String patientId);
	
	Request findByRequestCode(String requestCode);
}
