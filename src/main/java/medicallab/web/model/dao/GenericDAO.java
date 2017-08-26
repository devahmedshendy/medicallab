package medicallab.web.model.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {
	 Long create(T obj);

	 List<T> findAll();
	
	 T findById(Long id);

	 void update(T obj);
	
	 void delete(T obj);

	 Map<String, Object> findPagedList(Integer firstResult, 
			Integer maxResult, String nativeQueryString, String nativeCountQueryString);
}
