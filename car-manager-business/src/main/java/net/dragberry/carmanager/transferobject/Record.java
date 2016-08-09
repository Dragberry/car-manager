package net.dragberry.carmanager.transferobject;

import java.time.LocalDate;

/**
 * Class that represents a single row in excel document
 * 
 * @author Maksim Drahun
 *
 */
public class Record implements TransferObject {

	private static final long serialVersionUID = -8164969135404861744L;
	
	private Long index;
	
	private LocalDate date;
	
	private String description;
	
	private String type;
	
	private double costBYR;
	
	private double costUSD;
	
	private double costBYRDad;
	
	private double costUSDDad;
	
	private double tax;
	
	private double exchangeRate;
	
	private double exchangeRateReal;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Index=").append(index);
		sb.append("][Date=").append(date);
		sb.append("][Description=").append(description);
		sb.append("][Type=").append(type);
		sb.append("][CostBYR=").append(costBYR);
		sb.append("][CostUSD=").append(costUSD);
		sb.append("][CostBYRDad=").append(costBYRDad);
		sb.append("][CostUSDDad=").append(costUSDDad);
		sb.append("][tax=").append(tax);
		sb.append("][exchangeRate=").append(exchangeRate);
		sb.append("][exchangeRateReal=").append(exchangeRateReal);
		sb.append("]");
		return sb.toString();
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCostBYR() {
		return costBYR;
	}

	public void setCostBYR(double costBYR) {
		this.costBYR = costBYR;
	}

	public double getCostUSD() {
		return costUSD;
	}

	public void setCostUSD(double costUSD) {
		this.costUSD = costUSD;
	}

	public double getCostBYRDad() {
		return costBYRDad;
	}

	public void setCostBYRDad(double costBYRDad) {
		this.costBYRDad = costBYRDad;
	}

	public double getCostUSDDad() {
		return costUSDDad;
	}

	public void setCostUSDDad(double costUSDDad) {
		this.costUSDDad = costUSDDad;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getExchangeRateReal() {
		return exchangeRateReal;
	}

	public void setExchangeRateReal(double exchangeRateReal) {
		this.exchangeRateReal = exchangeRateReal;
	}
	
}
