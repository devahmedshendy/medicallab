package medicallab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MedicallabApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MedicallabApplication.class, args);
	}
	
	
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
}
