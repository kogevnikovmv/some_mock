package modelsJson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TickersItem{

	@JsonProperty("alerts")
	private List<AlertsItem> alerts;

	@JsonProperty("ticker")
	private String ticker;

	public TickersItem(String ticker, List<AlertsItem> alerts) {
	}

	public TickersItem(String ticker) {
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
		return ticker.equals(that.ticker);
	}
}