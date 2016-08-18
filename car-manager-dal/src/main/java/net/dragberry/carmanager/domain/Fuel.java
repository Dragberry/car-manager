package net.dragberry.carmanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Fuel implements Serializable {

	private static final long serialVersionUID = 6141793340217770908L;
	@Column(name = "FUEL_QUANTITY")
	private double quantity;
	@Column(name = "FUEL_TYPE", columnDefinition = "char")
	private String type;
	@Column(name = "FUEL_COST")
	private BigDecimal cost;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Fuel[").append("]\n");
		sb.append("\t[Type=").append(type).append("]\n");
		sb.append("\t[Cost=").append(cost).append("]\n");
		sb.append("\t[Quantity=").append(quantity).append("]\n");
		return sb.toString();
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}
