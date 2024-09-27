package org.lanit.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TickersItem{
	@JsonProperty("ticker")
	private String ticker;
	@JsonProperty("alerts")
	private List<AlertsItem> alerts;



	public TickersItem () {}
	public TickersItem(String ticker) {
		this.ticker=ticker;
	}
	public TickersItem(String ticker, List<AlertsItem> alerts) {
		this.ticker=ticker;
		this.alerts=alerts;
	}

	public void setAlerts(List<AlertsItem> alerts){
		this.alerts = alerts;
	}

	public List<AlertsItem> getAlerts(){
		return alerts;
	}

	public void setTicker(String ticker){
		this.ticker = ticker;
	}

	public String getTicker(){
		return ticker;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TickersItem that = (TickersItem) o;
		return Objects.equals(ticker, that.ticker);
	}
}