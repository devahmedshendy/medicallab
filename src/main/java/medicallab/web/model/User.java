package medicallab.web.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Entity
public class User implements Serializable, UserDetails {
	
	private static final long serialVersionUID = 6256454077460119383L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable=false, unique = true)
	private String username;

	@Column(nullable=false)
	private String hashedPassword;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Column(nullable=false)
	private String gender;
	
	@Column(columnDefinition="boolean default true", nullable=false)
	private boolean enabled = true;
	
	@Column(columnDefinition="datetime default CURRENT_TIMESTAMP", nullable=false)
	private Date createdAt = new Date();
	
	@Column(columnDefinition="datetime default CURRENT_TIMESTAMP", nullable=false)
	private Date updatedAt = new Date();
	
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)
	private List<UserAuthorities> roles;

	public User() { }
	
	public User(User user) {
		this.hashedPassword  = user.getHashedPassword();
		this.username  = user.getUsername();
		this.enabled   = user.isEnabled();
		
		this.firstname = user.getFirstname();
		this.lastname  = user.getLastname();
		
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
		
		this.roles     = user.getRoles();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return getHashedPassword();
	}


	public String getHashedPassword() {
		return hashedPassword;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public void setRoles(List<UserAuthorities> roles) {
		this.roles = roles;
	}
	
	public List<UserAuthorities> getRoles() {
		return roles;
	}


	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] userAuthorities = new String[roles.size()];
		
		for (int i = 0; i < userAuthorities.length; i++) {
			userAuthorities[i] = roles.get(i).getAuthority();
		} 
		
		return AuthorityUtils.createAuthorityList(userAuthorities);
	}


	public boolean isAccountNonExpired() { return true; }

	public boolean isAccountNonLocked() { return true; }

	public boolean isCredentialsNonExpired() { return true; }	
	
}
