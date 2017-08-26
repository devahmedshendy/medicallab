package medicallab.web.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DAOSessionFactory {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return (Session) sessionFactory.getCurrentSession();
	}
	
}
