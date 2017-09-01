package medicallab.web.model.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicallab.web.misc.PagedListHolder;
import medicallab.web.model.Role;
import medicallab.web.model.dao.RoleDAO;

@Transactional
@Service("userRoleService")
public class RoleServiceImpl implements RoleService {

	@Autowired RoleDAO roleDAO;
	
	@Override
	public Role findByRoleName(String roleName) {
		return roleDAO.findByRoleName(roleName);
	}

	@Override
	public void create(Role obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Role user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Role user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PagedListHolder<Role> findPagedList(Integer firstResult, Integer maxResult, Map<String, String> search,
			Map<String, String> sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findById(Object id) {
		// TODO Auto-generated method stub
		return null;
	}
}
