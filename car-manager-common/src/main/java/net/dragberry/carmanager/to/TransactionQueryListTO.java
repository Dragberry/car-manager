package net.dragberry.carmanager.to;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import net.dragberry.carmanager.common.Currency;

public class TransactionQueryListTO extends QueryListTO {

	private static final long serialVersionUID = -8216960599009522169L;
	
	private BigDecimal amountTo;
	
	private BigDecimal amountFrom;
	
	private List<Currency> currencyList;
	
	private Currency displayCurrency;
	
	private LocalDate dateTo;
	
	private LocalDate dateFrom;
	
	private List<Long> transactionTypeKeyList;
	
	private Long carKey;
	
	private Long carOwnerKey;
	
	private Long customerKey;
	
	private Double fuelQuantityTo;

	private Double fuelQuantityFrom;
	
	public Long getCarOwnerKey() {
		return carOwnerKey;
	}

	public void setCarOwnerKey(Long carOwnerKey) {
		this.carOwnerKey = carOwnerKey;
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}

	public BigDecimal getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(BigDecimal amountFrom) {
		this.amountFrom = amountFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Long getCarKey() {
		return carKey;
	}

	public void setCarKey(Long carKey) {
		this.carKey = carKey;
	}

	public Long getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(Long customerKey) {
		this.customerKey = customerKey;
	}

	public Double getFuelQuantityTo() {
		return fuelQuantityTo;
	}

	public void setFuelQuantityTo(Double fuelQuantityTo) {
		this.fuelQuantityTo = fuelQuantityTo;
	}

	public Double getFuelQuantityFrom() {
		return fuelQuantityFrom;
	}

	public void setFuelQuantityFrom(Double fuelQuantityFrom) {
		this.fuelQuantityFrom = fuelQuantityFrom;
	}

	public List<Long> getTransactionTypeKeyList() {
		return transactionTypeKeyList;
	}

	public void setTransactionTypeKeyList(List<Long> transactionTypeKeyList) {
		this.transactionTypeKeyList = transactionTypeKeyList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public Currency getDisplayCurrency() {
		return displayCurrency;
	}

	public void setDisplayCurrency(Currency displayCurrency) {
		this.displayCurrency = displayCurrency;
	}
	
}
