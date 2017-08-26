package medicallab.web.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import medicallab.web.model.Role;

@Component("roleDAO")
public class RoleDAOImpl extends DAOSessionFactory implements RoleDAO {
	
	@Override
	public Role findByRoleName(String roleName) {
		return getSession()
				.createQuery("from Role where roleName = :roleName", Role.class)
				.setParameter("roleName", roleName)
				.getSingleResult();
	}

	@Override
	public Long create(Role obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Role obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Role obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> findPagedList(Integer firstResult, Integer maxResult, String nativeQueryString,
			String nativeCountQueryString) {
		// TODO Auto-generated method stub
		return null;
	}
}
