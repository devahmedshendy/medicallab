package medicallab.web.dao;

import org.springframework.stereotype.Component;
import medicallab.web.model.User;

@Component("userDAO")
public class UserDAOImpl extends AbstractDAO implements UserDAO {
	
	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public User findByUsername(String username) {
		return  getSession()
				.createQuery("from User where username = :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
	}
	
	@Override
	public User findById(Long id) {
		return  getSession()
				.createQuery("from User where id = :userId", User.class)
				.setParameter("userId", id)
				.getSingleResult();
	}
	

	@Override
	public void update(User user) {
		getSession().merge(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}
}
