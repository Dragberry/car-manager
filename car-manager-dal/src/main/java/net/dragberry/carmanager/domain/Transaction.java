package net.dragberry.carmanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
@AttributeOverrides({ @AttributeOverride(column = @Column(name = "TRANSACTION_KEY") , name = "entityKey") })
public class Transaction extends AbstractEntity {

	private static final long serialVersionUID = -1124381093271131117L;
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	@Column(name = "CURRENCY", columnDefinition = "char")
	private String currency;
	@Column(name = "EXCHANGE_RATE")
	private Double exchangeRate;
	@Column(name = "EXECUTION_DATE")
	private LocalDate executionDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSACTION_TYPE_KEY", referencedColumnName = "TRANSACTION_TYPE_KEY")
	private TransactionType transactionType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAR_KEY", referencedColumnName = "CAR_KEY")
	private Car car;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "CUSTOMER_KEY")
	private Customer customer;

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

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
