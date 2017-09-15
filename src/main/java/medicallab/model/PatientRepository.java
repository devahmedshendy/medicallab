package medicallab.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

	Patient findByPatientId(String patientId);
	
	@Query("SELECT p.profileImage as profileImage FROM Patient p WHERE p.patientId = ?1")
	byte[] findProfileImageForPatientId(String patientId);
}
