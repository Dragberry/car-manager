package net.dragberry.carmanager.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_TYPE")
@AttributeOverrides({
	@AttributeOverride(column = @Column(name =  "TRANSACTION_TYPE_KEY"), name = "entityKey")
})
public class TransactionType extends AbstractEntity {

	private static final long serialVersionUID = 8181044143628392301L;
	
	public static final String FUEL = "Fuel";
	public static final Long FUEL_KEY = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
