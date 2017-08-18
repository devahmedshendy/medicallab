package medicallab.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserAuthorities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName = "id", nullable=false)
	private User user;
	
	@Column(nullable=false)
	private String authority;

	
	public Long getid() {
		return id;
	}

	
	public User getUser() {
		return user;
	}

	public String getAuthority() {
		return authority;
	}
	
	
	
}
