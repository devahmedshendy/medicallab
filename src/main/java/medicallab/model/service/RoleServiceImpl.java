package medicallab.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import medicallab.model.Role;
import medicallab.model.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepo;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepo = roleRepository;
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleRepo.findByRoleName(roleName);
	}

}
