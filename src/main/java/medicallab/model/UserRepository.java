package medicallab.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	User findByUsername(String username);
	Page<User> findByFullnameStartingWith(String searchText, Pageable pageable);
	Page<User> findByUsernameStartingWith(String searchText, Pageable pageable);
	
}
