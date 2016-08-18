package net.dragberry.carmanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.dragberry.carmanager.common.Currency;

@Entity
@Table(name = "TRANSACTION")
@AttributeOverrides({ 
	@AttributeOverride(column = @Column(name = "TRANSACTION_KEY") , name = "entityKey") 
})
public class Transaction extends AbstractEntity {

	private static final long serialVersionUID = -1124381093271131117L;
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CURRENCY")
	@Enumerated(EnumType.STRING)
	private Currency currency;
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
	@Embedded
	private Fuel fuel;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append("[").append(getEntityKey()).append("]\n");
		sb.append("\t[Description=").append(description).append("]\n");
		sb.append("\t[Type=").append(transactionType == null ? null : transactionType.getName()).append("]\n");
		sb.append("\t[Amount=").append(amount).append(currency).append("]\n");
		sb.append("\t[ExchangeRate=").append(exchangeRate).append("]\n");
		sb.append("\t[ExecutionDate=").append(executionDate).append("]\n");
		sb.append("\t[Customer=").append(customer == null ? null : customer.getCustomerName()).append("]\n");
		return sb.toString();
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}
	
}
