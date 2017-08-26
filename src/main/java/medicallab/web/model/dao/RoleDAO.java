package medicallab.web.model.dao;

import medicallab.web.model.Role;

public interface RoleDAO extends GenericDAO<Role> {
	Role findByRoleName(String roleName);
}