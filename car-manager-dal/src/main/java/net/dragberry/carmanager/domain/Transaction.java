package net.dragberry.carmanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;

@Entity
@Table(name = "TRANSACTION")
@TableGenerator(
		name = "TRANSACTION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "TRANSACTION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
@NamedQueries({
	@NamedQuery(
			name = Transaction.FIND_BY_STATUS,
			query = "select t from Transaction t where t.status = :status")
})
public class Transaction extends AbstractEntity {

	private static final long serialVersionUID = -1124381093271131117L;
	
	public final static String FIND_BY_STATUS = "Transaction.findByStatus";
	@Id
	@Column(name = "TRANSACTION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRANSACTION_GEN")
	private Long entityKey;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREDITOR_KEY", referencedColumnName = "CUSTOMER_KEY")
	private Customer creditor;
	
	@Embedded
	private Fuel fuel;
	
	@Column(name = "STATUS")
	@Convert(converter = TransactionStatusConverter.class)
	private TransactionStatus status;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

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

	public Customer getCreditor() {
		return creditor;
	}

	public void setCreditor(Customer creditor) {
		this.creditor = creditor;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	
}
