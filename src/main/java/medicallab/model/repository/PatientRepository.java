package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import medicallab.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

	Patient findByPatientId(String patientId);
	
	@Query("SELECT p.profileImage as profileImage FROM patient p WHERE p.patientId = ?1")
	byte[] findProfileImageForPatientId(String patientId);
	
//	PatientForRequest findFullnameAndPatientId(String patientId);
}
