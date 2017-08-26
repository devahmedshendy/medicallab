package medicallab.web.model.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import org.springframework.stereotype.Component;
import medicallab.web.model.User;

@Component("userDAO")
public class UserDAOImpl extends DAOSessionFactory implements UserDAO {
	
	@Override
	public Long create(User user) {
		return (Long) getSession().save(user);
	}
	
	@Override
	public List<User> findAll() {
		return getSession()
				.createQuery("from User", User.class)
				.getResultList();
	}
	
	@Override
	public User findById(Long id) {
		User user;
		try {
			user =  getSession()
					.createQuery("from User where id = :userId", User.class)
					.setParameter("userId", id)
					.getSingleResult();
			
		} catch(NoResultException e) {
			user = null;
		}
		
		return user;
	}
	
	@Override
	public Map<String, Object> findPagedList(Integer startPosition, Integer maxResult, String nativeQueryString, String nativeCountQueryString) {
		System.out.println("The native query: " + nativeQueryString);
		
		
		List<User> userList = getSession()
					.createNativeQuery(nativeQueryString, User.class)
					.setFirstResult(startPosition)
					.setMaxResults(maxResult)
					.getResultList();
		
		BigInteger resultCount = (BigInteger) getSession()
					.createNativeQuery(nativeCountQueryString)
					.getSingleResult();
		
		System.out.println("resultCount: " + resultCount);
		
		Map<String, Object> result = new HashMap<>();
		result.put("pagedList", userList);
		result.put("noOfElements", resultCount.intValue());
		
		return result;
	}
	
	
	@Override
	public User findByUsername(String username) {
		User user;
		try {
			user =  getSession()
					.createQuery("from User where username = :username", User.class)
					.setParameter("username", username)
					.getSingleResult();
			
		} catch(NoResultException e) {
			user = null;
		}
		
		return user;
	}
	
	@Override
	public void update(User user) {
		getSession().update(user);
	}
	
	@Override
	public void delete(User user) {
		getSession().delete(user);
	}
		
}
