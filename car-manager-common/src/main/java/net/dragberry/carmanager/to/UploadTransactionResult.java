package net.dragberry.carmanager.to;

public class UploadTransactionResult implements TransferObject {

	private static final long serialVersionUID = 3479649776355807678L;
	
	private Integer successfullTransactions;
	
	private Integer failedTransactions;

	public Integer getSuccessfullTransactions() {
		return successfullTransactions;
	}

	public void setSuccessfullTransactions(Integer successfullTransactions) {
		this.successfullTransactions = successfullTransactions;
	}

	public Integer getFailedTransactions() {
		return failedTransactions;
	}

	public void setFailedTransactions(Integer failedTransactions) {
		this.failedTransactions = failedTransactions;
	}
	

}
