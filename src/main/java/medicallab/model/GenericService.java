package medicallab.model;

import java.util.Map;

import org.springframework.data.domain.Page;

public interface GenericService<T> {
	void create(T obj);
	
	void update(T entity);

	void delete(T entity);
	
	Page<T> findPagedList(Map<String, String> queryParams) throws NumberFormatException;
	
	void populateFormIntoObject(Object obj, T entity);
	void populateFormFromObject(Object obj, T entity);

}
