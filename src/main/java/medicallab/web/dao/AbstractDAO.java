package medicallab.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDAO {
	
	@Autowired
	SessionFactory sessionFactory;
	
	protected Session getSession() {
		return (Session) sessionFactory.getCurrentSession();
	}
	
}
