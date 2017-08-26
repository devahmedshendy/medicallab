package medicallab.web.misc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component("pagedListHolder")
public class PagedListHolder<T> {
	
	private Integer firstResult;
	private Integer maxResult;
	private List<T> pagedList;
	private Integer pageSize;
	private Integer noOfElements;

	public PagedListHolder() {
		this.firstResult		= 0;
		this.maxResult		= 10;
		this.pagedList		= new ArrayList<>();
		this.pageSize		= 0;
		this.noOfElements	= 0;
	}
	
	public List<T> getPagedList() {
		return this.pagedList;
	}

	public void setPagedList(List<T> pagedList) {
		this.pagedList = pagedList;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getPageSize() {
		return this.pageSize;
	}
	
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}
	
	public Integer getNoOfElements() {
		return this.noOfElements;
	}
	
	public boolean isLastPage() {
		return ( (firstResult + maxResult) >= noOfElements ) ? true : false;
	}
	
	public boolean isFirstPage() {
		return this.firstResult == 0 ? true : false;
	}
	
}
