package medicallab.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of= {"username"})
@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Transient
	private String fullname;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String hashedPassword;

	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false, columnDefinition="boolean default true")
	private boolean enabled = true;
	
	@Column(nullable = false, columnDefinition="datetime default CURRENT_TIMESTAMP")
	private Date createdAt = new Date();
	
	@Column(nullable = false, columnDefinition="datetime default CURRENT_TIMESTAMP")
	private Date updatedAt = new Date();

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleName", referencedColumnName = "roleName", nullable = false)
	private Role role;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(new String[] { role.getRoleName() });
	}

	@Override
	public String getPassword() {
		return hashedPassword;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@PostLoad
	public void setFullname() {
		fullname = firstname + " " + lastname;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	
	public String getRoleDescription() {
		switch(role.getRoleName()) {
			case "ROLE_ROOT":
				return "Super User";
				
			case "ROLE_ADMIN":
				return "System Administrator";
				
			case "ROLE_DOCTOR":
				return "Investigation Doctor";
				
			case "ROLE_OFFICER":
				return "Registration Officer";
				
			default:
				return "";
		}
	}
}
