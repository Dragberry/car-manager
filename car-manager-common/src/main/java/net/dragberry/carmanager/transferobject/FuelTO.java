package net.dragberry.carmanager.transferobject;

import java.math.BigDecimal;

public class FuelTO implements TransferObject {

	private static final long serialVersionUID = -3211556035025074390L;
	
	private BigDecimal cost;
	
	private Double quantity;
	
	private String type;
	
	public FuelTO() {}
	
	public FuelTO(BigDecimal cost, Double quantity, String type) {
		this.cost = cost;
		this.quantity = quantity;
		this.type = type;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
