package net.dragberry.carmanager.domain;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FUEL")
@AttributeOverrides({
	@AttributeOverride(column = @Column(name =  "FUEL_KEY"), name = "entityKey")
})
public class Fuel extends AbstractEntity {

	private static final long serialVersionUID = 6141793340217770908L;
	@Column(name = "QUANTITY")
	private double quantity;
	@Column(name = "TYPE", columnDefinition = "char")
	private String type;
	@Column(name = "COST")
	private BigDecimal cost;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSACTION_KEY", referencedColumnName = "TRANSACTION_KEY")
	private Transaction transaction;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Fuel[").append(getEntityKey()).append("]\n");
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
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
}
