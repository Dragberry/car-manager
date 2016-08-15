package net.dragberry.carmanager.transferobject;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The transfer object that represents a single transaction
 * 
 * @author Maksim Drahun
 *
 */
public class TransactionTO implements TransferObject {

	private static final long serialVersionUID = -7476859106416774174L;
	
	private Long transactionKey;
	
	private String description;
	
	private BigDecimal amount;
	
	private String currency;
	
	private Double exchangeRate;
	
	private LocalDate executionDate;
	
	private Long customerKey;
	
	private Long carKey;
	
	private Long transactionTypeKey;
	
	private FuelTO fuel;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTransactionKey() {
		return transactionKey;
	}

	public void setTransactionKey(Long transactionKey) {
		this.transactionKey = transactionKey;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

	public Long getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(Long customerKey) {
		this.customerKey = customerKey;
	}

	public Long getCarKey() {
		return carKey;
	}

	public void setCarKey(Long carKey) {
		this.carKey = carKey;
	}

	public Long getTransactionTypeKey() {
		return transactionTypeKey;
	}

	public void setTransactionTypeKey(Long transactionTypeKey) {
		this.transactionTypeKey = transactionTypeKey;
	}

	public FuelTO getFuel() {
		return fuel;
	}

	public void setFuel(FuelTO fuel) {
		this.fuel = fuel;
	}
	
}
