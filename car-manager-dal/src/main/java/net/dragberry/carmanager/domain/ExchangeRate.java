package net.dragberry.carmanager.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import net.dragberry.carmanager.common.Currency;

@Entity
@Table(name = "EXCHANGE_RATE")
@NamedQueries({
	@NamedQuery(
			name = ExchangeRate.FIND_LAST_PROCESSED_DATE,
			query = "select max(e.date) from ExchangeRate e where e.baseCurrency = :baseCurrency and e.currency = :currency"),
	@NamedQuery(
			name = ExchangeRate.FIND_EXCHANGE_RATE,
			query = "select e.exRate from ExchangeRate e where e.date = :date and e.baseCurrency = :baseCurrency and e.currency = :currency")
})
@TableGenerator(
		name = "EXCHANGE_RATE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "EXCHANGE_RATE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class ExchangeRate extends AbstractEntity {

	private static final long serialVersionUID = -7394183291663069396L;
	
	public final static String FIND_LAST_PROCESSED_DATE = "ExchangeRate.findLastProcessedDate";
	public final static String FIND_EXCHANGE_RATE = "ExchangeRate.findExchangeRate";
	
	@Id
	@Column(name = "EXCHANGE_RATE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EXCHANGE_RATE_GEN")
	private Long entityKey;
	
	@Column(name = "DATE")
	private LocalDate date;
	
	@Column(name = "ORIGINAL_CURRENCY")
	@Enumerated(EnumType.STRING)
	private Currency baseCurrency;
	
	@Column(name = "CURRENCY")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column(name = "EXCHANGE_RATE")
	private Double exRate;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Double getExRate() {
		return exRate;
	}

	public void setExRate(Double exRate) {
		this.exRate = exRate;
	}

}
