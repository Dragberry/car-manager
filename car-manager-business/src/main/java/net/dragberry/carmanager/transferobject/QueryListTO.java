package net.dragberry.carmanager.transferobject;

public class QueryListTO implements TransferObject {

	private static final long serialVersionUID = 3666265113917750276L;
	
	private int pageNumber = 0;
	
	private int pageSize = 20;
	
	public QueryListTO() {
	}
	
	public QueryListTO(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
