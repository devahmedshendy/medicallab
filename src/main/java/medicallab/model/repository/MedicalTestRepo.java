package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import medicallab.model.MedicalTest;

@Repository
public interface MedicalTestRepo extends JpaRepository<MedicalTest, String>, JpaSpecificationExecutor<MedicalTest> {

}
