package net.dragberry.carmanager.ws.json;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExRate implements Serializable {

	private static final long serialVersionUID = 683295946595145846L;
	
	private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	@JsonProperty("Cur_ID")
	private Long id;
	@JsonProperty("Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
	private LocalDateTime date;
	@JsonProperty("Cur_Abbreviation")
	private String currencyCode;
	@JsonProperty("Cur_Scale")
	private Integer scale;
	@JsonProperty("Cur_OfficialRate")
	private Double rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
