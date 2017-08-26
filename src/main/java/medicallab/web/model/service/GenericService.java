package medicallab.web.model.service;

import java.util.List;
import java.util.Map;

import medicallab.web.misc.PagedListHolder;

public interface GenericService<T> {
	void create(T obj);
	
	List<T> findAll();
	
	void update(T user);

	void delete(T user);
	
	PagedListHolder<T> findPagedList(Integer firstResult, 
			Integer maxResult, Map<String, String> search,
			Map<String, String> sort);
}
