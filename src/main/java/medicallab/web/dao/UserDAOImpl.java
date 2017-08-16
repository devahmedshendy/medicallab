package medicallab.web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import medicallab.web.model.User;

@Component
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public User findByUsername(String username) {
		return (User) sessionFactory.createEntityManager()
						.createQuery("from User where username = :username")
						.setParameter("username", username)
						.getSingleResult();	
	}
}
