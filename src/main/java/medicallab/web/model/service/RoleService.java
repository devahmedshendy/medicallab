package medicallab.web.model.service;

import medicallab.web.model.Role;

public interface RoleService extends GenericService<Role> {
	Role findByRoleName(String roleName);
}
