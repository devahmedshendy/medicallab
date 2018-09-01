package medicallab.model.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import medicallab.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	User findByUsername(String username);
	
	@Query(value = "FROM user WHERE role_name = 'ROLE_DOCTOR' ORDER BY CONCAT(firstname, ' ', lastname)")
	List<User> findByDoctorRole();
	
	Page<User> findByFullnameStartingWith(String searchText, Pageable pageable);
	Page<User> findByUsernameStartingWith(String searchText, Pageable pageable);
	
}
