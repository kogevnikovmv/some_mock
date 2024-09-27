package modelsJson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertsItem{

	@JsonProperty("percent")
	private int percent;

	@JsonProperty("timeFrame")
	private int timeFrame;

	public AlertsItem(int timeFrame, int percent) {
	}

	public void setPercent(int percent){
		this.percent = percent;
	}

	public int getPercent(){
		return percent;
	}

	public void setTimeFrame(int timeFrame){
		this.timeFrame = timeFrame;
	}

	public int getTimeFrame(){
		return timeFrame;
	}
}