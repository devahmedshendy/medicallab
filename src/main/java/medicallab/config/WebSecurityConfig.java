package medicallab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired private UserDetailsService userService;
	@Autowired private BCryptPasswordEncoder bcryptPasswordEncoder;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		auth
			.userDetailsService(userService)
			.passwordEncoder(bcryptPasswordEncoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/images/**").permitAll()
				.antMatchers("/login**").permitAll()
				.antMatchers("/users/**").hasRole("ADMIN")
				.antMatchers("/patients/**", "/requests/**", "/test/**").hasAnyRole(new String[] {"ADMIN", "OFFICER", "DOCTOR"})
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
			.sessionManagement()
				.maximumSessions(1).maxSessionsPreventsLogin(false)
					.expiredUrl("/login?expired")
					.sessionRegistry(getSessionRegistry())
			;
		
	}
	
	@Bean("sessionRegistry")
	public SessionRegistry getSessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean("bcryptPasswordEncoder")
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
