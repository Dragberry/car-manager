package net.dragberry.carmanager.to;

public class TransactionTypeTO implements TransferObject {

	private static final long serialVersionUID = 8213311427139538177L;
	
	private Long transactionTypeKey;
	
	private String name;

	public Long getTransactionTypeKey() {
		return transactionTypeKey;
	}

	public void setTransactionTypeKey(Long transactionTypeKey) {
		this.transactionTypeKey = transactionTypeKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
