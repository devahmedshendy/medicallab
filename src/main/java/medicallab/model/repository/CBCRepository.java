package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import medicallab.model.CBC;

public interface CBCRepository extends JpaRepository<CBC, Long> {

	CBC findByRequestCode(String requestCode);
	
	void deleteByRequestCode(String requestCode);

}
