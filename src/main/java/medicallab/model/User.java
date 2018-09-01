package medicallab.model;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(of= {"username"})
@Entity(name = "user")
public class User implements UserDetails {
	private static final long serialVersionUID = -1011199116719289329L;

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
	
	@Column(name = "hashed_password", nullable = false)
	private String hashedPassword;

	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false, columnDefinition="boolean default true")
	private boolean enabled = true;
	
	@Column(name = "created_at", nullable = false, columnDefinition="datetime default CURRENT_TIMESTAMP")
	private Date createdAt = new Date();
	
	@Column(name = "updated_at", nullable = false, columnDefinition="datetime default CURRENT_TIMESTAMP")
	private Date updatedAt = new Date();

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_name", referencedColumnName = "role_name", nullable = false)
	private Role role;
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
//	private Collection<CBC> cbcList = new ArrayList<>();
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
//	private Collection<UMT> umtList = new ArrayList<>();
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
//	private Collection<FMT> fmtList = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
	private Collection<MedicalRequest> medicalRequest = new ArrayList<>();
	
	
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
		setFullname(null);
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname == null ? firstname + " " + lastname : fullname;
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
