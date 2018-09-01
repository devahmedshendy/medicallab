package medicallab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	public WebSecurityConfig(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/users**").hasAnyRole("ADMIN", "ROOT")
				.antMatchers("/patients/new**", "/patients/edit**", "/requests/new**", "/requests/edit**").hasAnyRole("OFFICER", "ROOT")
				.antMatchers("/patients/**", "/requests/**").hasAnyRole("ADMIN", "OFFICER", "DOCTOR", "ROOT")
				.antMatchers("/login**", "/uri**").permitAll()
				.antMatchers("/js/**", "/css/**", "/images/**", "**/favicon.ico").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.and()
			.logout().permitAll()
			    .deleteCookies("JSESSIONID", "remember-me")
				.and()
			.rememberMe()
				.tokenValiditySeconds(604800) // One Week
				.and()
			.sessionManagement()
				.maximumSessions(1)
					.sessionRegistry(sessionRegistry)
					.maxSessionsPreventsLogin(false)
					.expiredUrl("/login?expired")
		;
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth,
			UserDetailsService userDetailsService, BCryptPasswordEncoder bcryptPasswordEncoder) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bcryptPasswordEncoder);
	}
}
