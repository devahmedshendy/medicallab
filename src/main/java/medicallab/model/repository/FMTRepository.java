package medicallab.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import medicallab.model.FMT;

public interface FMTRepository extends JpaRepository<FMT, Long> {

	FMT findByRequestCode(String requestCode);

	void deleteByRequestCode(String requestCode);

}
