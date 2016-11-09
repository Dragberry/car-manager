package net.dragberry.carmanager.common;

import java.text.MessageFormat;

public enum TransactionStatus {

	ACTIVE('A'), PROCESSING('P');
	
	private static final String UNKNOWN_VALUE_MSG = "Unknown TransactionStatus value: {0}!";
	private static final String NPE_MSG = "Transaction status cannot be null!";
	private char value;
	
	private TransactionStatus(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return value;
	}
	
	public static TransactionStatus valueOf(Character value) {
		if (value == null) {
			throw new NullPointerException(NPE_MSG);
		}
		for (TransactionStatus status : TransactionStatus.values()) {
			if (value.equals(status.getValue())) {
				return status;
			}
		}
		throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, value));
	}
}
