package medicallab.model.service;

import medicallab.model.Role;

public interface RoleService {
	Role findByRoleName(String roleName);
}
