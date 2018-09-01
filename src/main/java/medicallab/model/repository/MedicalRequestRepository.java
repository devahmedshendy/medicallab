package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import medicallab.model.MedicalRequest;

@Repository
public interface MedicalRequestRepository extends JpaRepository<MedicalRequest, String>, JpaSpecificationExecutor<MedicalRequest>  {

}
