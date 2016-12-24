package net.dragberry.carmanager.to;

public class UploadTransactionResult implements TransferObject {

	private static final long serialVersionUID = 3479649776355807678L;
	
	private int successfulTransactions;
	
	private int failedTransactions;

	public Integer getSuccessfulTransactions() {
		return successfulTransactions;
	}

	public void addSuccessfulTransactions(int successfulTransactions) {
		this.successfulTransactions += successfulTransactions;
	}

	public Integer getFailedTransactions() {
		return failedTransactions;
	}

	public void addFailedTransactions(int failedTransactions) {
		this.failedTransactions += failedTransactions;
	}

	public void add(UploadTransactionResult resultToAdd) {
		this.successfulTransactions += resultToAdd.successfulTransactions;
		this.failedTransactions += resultToAdd.failedTransactions;
	}
	

}
