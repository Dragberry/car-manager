package net.dragberry.carmanager.to;

import java.util.ArrayList;
import java.util.List;

public class ResultList<T extends TransferObject> implements TransferObject {

	private static final long serialVersionUID = -808530806774688907L;
	
	private long pageNumber;
	
	private long pageSize;
	
	private long totalCount;
	
	private List<T> result = new ArrayList<>();
	
	public void addItem(T item) {
		result.add(item);
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	
}
