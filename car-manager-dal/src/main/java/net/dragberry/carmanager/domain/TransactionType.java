package net.dragberry.carmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "TRANSACTION_TYPE")
@NamedQueries({
	@NamedQuery(
			name = TransactionType.FIND_BY_NAME_QUERY,
			query = "select tt from TransactionType tt where tt.name = :name")
})
@TableGenerator(
		name = "TRANSACTION_TYPE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "TRANSACTION_TYPE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class TransactionType extends AbstractEntity {

	private static final long serialVersionUID = 8181044143628392301L;

	public static final String FIND_BY_NAME_QUERY = "TransactionType.findByName";
	
	public static final String FUEL = "Fuel";
	public static final Long FUEL_KEY = 1000L;
	
	public static final String LOAN_PAYMENT = "LoanPayment";
	public static final Long LOAN_PAYMENT_KEY = 1001L;
	
	@Id
	@Column(name = "TRANSACTION_TYPE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRANSACTION_TYPE_GEN")
	private Long entityKey;
	
	private String name;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
