package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import medicallab.model.UMT;

public interface UMTRepository extends JpaRepository<UMT, Long> {

	UMT findByRequestCode(String requestCode);

	void deleteByRequestCode(String requestCode);

}
